/**
 * 
 */
package nl.karnhuis.mqttserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author werner
 *
 */
public class RemainingLengthInfo {

    private int remainingNumberOfBytes = 0;
    private int numberOfBytesForLengthValue = 0;
    private byte[] theLengthBytes = new byte[4];

    public void setTheLengthBytes(byte[] theBytes) {
	if (theBytes.length > 4) {
	    // not possible
	}
	theLengthBytes = theBytes;
	decode();
    }

    public void setRemainingNumberOfBytes(int quantity) {
	if (quantity < 1) {
	    // Not possible
	}
	remainingNumberOfBytes = quantity;
	encode();
    }

    public int getRemainingNumberOfBytes() {
	return remainingNumberOfBytes;
    }

    public int getNumberOfBytesForLengthValue() {
	return numberOfBytesForLengthValue;
    }

    public byte[] getTheLengthBytes() {
	if (numberOfBytesForLengthValue == 4) {
	    return theLengthBytes;
	} else if (numberOfBytesForLengthValue == 3) {
	    byte[] newByteArray = new byte[3];
	    newByteArray[0] = theLengthBytes[0];
	    newByteArray[1] = theLengthBytes[1];
	    newByteArray[2] = theLengthBytes[2];
	    return newByteArray;
	} else if (numberOfBytesForLengthValue == 2) {
	    byte[] newByteArray = new byte[2];
	    newByteArray[0] = theLengthBytes[0];
	    newByteArray[1] = theLengthBytes[1];
	    return newByteArray;
	} else if (numberOfBytesForLengthValue == 1) {
	    byte[] newByteArray = new byte[1];
	    newByteArray[0] = theLengthBytes[0];
	    return newByteArray;
	}
	return new byte[0];
    }

    private void encode() {
	byte encodedByte = 0;
	List<Byte> collectionOfBytes = new ArrayList<Byte>();
	do {
	    encodedByte = (byte) (remainingNumberOfBytes % 128);
	    remainingNumberOfBytes = remainingNumberOfBytes / 128;
	    // if there is more data to encode, set the top bit of this byte
	    if (remainingNumberOfBytes > 0) {
		encodedByte = (byte) (encodedByte | 128);
	    }
	    collectionOfBytes.add(encodedByte);
	} while (remainingNumberOfBytes > 0);
	byte[] theLength = new byte[collectionOfBytes.size()];
	for (int index = 0; index < collectionOfBytes.size(); index++) {
	    theLength[index] = collectionOfBytes.get(index);
	}
	theLengthBytes = theLength;
	numberOfBytesForLengthValue = theLength.length;
    }

    private void decode() {
	int multiplier = 1;
	int value = 0;
	int encodedByte = 0;
	int index = 0;
	do {
	    encodedByte = theLengthBytes[index] & 0xFF;
	    value += (encodedByte & 127) * multiplier;
	    multiplier *= 128;
	    if (multiplier > 128 * 128 * 128) {
		System.out.println("Malformed Remaining Length");
	    }
	    index++;
	} while ((encodedByte & 128) != 0);
	remainingNumberOfBytes = value;
	numberOfBytesForLengthValue = index;
    }

}
