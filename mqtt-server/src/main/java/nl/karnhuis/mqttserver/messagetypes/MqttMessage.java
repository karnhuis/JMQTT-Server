/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

import nl.karnhuis.mqttserver.PacketType;
import nl.karnhuis.mqttserver.RemainingLengthInfo;

/**
 * @author werner
 *
 */
public class MqttMessage extends Message {

    private PacketType packetType = PacketType.UNKNOWN;

    private boolean retain = false;
    private boolean dup = false;
    private int qualityOfService = 0;

    private RemainingLengthInfo rli = new RemainingLengthInfo();

    private int messageLength = 1;
    private int numberOfLenghtBytes = 1;

    private int packetId = 0;

    private byte[] payload;

    public MqttMessage(byte[] theMessage) {
	setMessageAsBytes(theMessage);
    }

    public void setRemainingNumberOfBytes(int value) {
	messageLength = value;
    }

    public void setLengthBytes(int numberOfBytes) {
	numberOfLenghtBytes = numberOfBytes;
    }

    public byte[] getRemainingBytes() {
	return payload;
    }

    public void setMessage(byte[] theMessage) {
	setMessageAsBytes(theMessage);
    }

    public void handleMessage() {
	packetType = PacketType.getType(((messageAsBytes[0] & 0x00F0) >> 4));
	determineFlags((byte) ((messageAsBytes[0] & 0x000F)));
	byte[] possibleLengthBytes = new byte[4];
	possibleLengthBytes[0] = messageAsBytes[1];
	possibleLengthBytes[1] = messageAsBytes[2];
	possibleLengthBytes[2] = messageAsBytes[3];
	possibleLengthBytes[3] = messageAsBytes[4];
	RemainingLengthInfo rli = new RemainingLengthInfo();
	rli.setTheLengthBytes(possibleLengthBytes);
	messageLength = rli.getRemainingNumberOfBytes();
	numberOfLenghtBytes = rli.getNumberOfBytesForLengthValue();
	packetId = determinePacketId();
	payload = determinePayload();
    }

    private byte[] determinePayload() {
	int start = 0;

	// These message types MUST have a payload.
	if (packetType == PacketType.CONNECT || packetType == PacketType.SUBSCRIBE || packetType == PacketType.SUBACK
		|| packetType == PacketType.UNSUBSCRIBE) {
	    if (packetId == 0) {
		// not allowed do something about it
	    } else {
		start = 1 + numberOfLenghtBytes + 2;
	    }
	    // The PUBLISH message CAN have a payload.
	} else if (packetType == PacketType.PUBLISH) {
	    if (packetId == 0) {
		start = 1 + numberOfLenghtBytes;
	    } else {
		start = 1 + numberOfLenghtBytes + 2;
	    }
	}
	byte[] payloadBytes = new byte[messageAsBytes.length - start];
	for (int index = 0; index < payloadBytes.length; index++) {
	    payloadBytes[index] = messageAsBytes[start + index];
	}
	return payloadBytes;
    }

    private int determinePacketId() {
	if (packetType == PacketType.CONNECT || packetType == PacketType.CONNACK || packetType == PacketType.PINGREQ
		|| packetType == PacketType.PINGRESP || packetType == PacketType.FORBIDDEN
		|| (packetType == PacketType.CONNECT && qualityOfService == 0)) {
	    return 0;
	}
	int msb = messageAsBytes[1 + numberOfLenghtBytes];
	int lsb = messageAsBytes[1 + numberOfLenghtBytes + 1];
	msb = msb << 8;
	msb = msb | lsb;
	return msb;
    }

    /**
     * @param flags
     */
    private void determineFlags(byte flags) {
	boolean qos1 = false;
	boolean qos2 = false;
	if ((flags & 0x01) == 0x01) {
	    retain = true;
	}
	if ((flags & 0x02) == 0x02) {
	    qos1 = true;
	}
	if ((flags & 0x04) == 0x04) {
	    qos2 = true;
	}
	if ((flags & 0x08) == 0x08) {
	    dup = true;
	}
	if (qos1 && qos2) {
	    qualityOfService = 3;
	}
	if (qos1 && !qos2) {
	    qualityOfService = 1;
	}
	if (!qos1 && qos2) {
	    qualityOfService = 2;
	}
    }

    public int getMessageType() {
	// TODO Auto-generated method stub
	return 0;
    }

}
