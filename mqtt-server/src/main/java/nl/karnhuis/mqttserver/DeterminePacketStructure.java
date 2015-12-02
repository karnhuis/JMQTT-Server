package nl.karnhuis.mqttserver;

import nl.karnhuis.mqttserver.exceptions.ForbiddenPacketTypeException;

public class DeterminePacketStructure {

    private boolean retain = false;
    private boolean dup = false;
    private boolean qos1 = false;
    private boolean qos2 = false;

    // read bytes
    // Neem eerste byte om fixed header te bepalen
    // Neem volgende byte(s) om hele fixed header in te lezen
    // Neem volgende byte(s) voor Variabele header (Als bestaat)
    // Neem volgende byte(s) als payload.
    // Nog een keer.

    /**
     * This method will examine the given byte array and handle accordingly
     * 
     * @param theReadBytes
     *            The bytes to examine
     * @throws ForbiddenPacketTypeException
     *             thrown if the byte array contains unexpected bytes.
     */
    public void readBytes(byte[] theReadBytes) throws ForbiddenPacketTypeException {

	MqttMessage mqttMessage = new MqttMessage();
	mqttMessage.setMessage(theReadBytes);
	for (int index = 0; index < theReadBytes.length; index++) {
	    byte[] remainingBytes;
	    int remainingLength = 0;
	    byte packetType = (byte) ((theReadBytes[0] & 0x00F0) >> 4);
	    byte flags = (byte) ((theReadBytes[0] & 0x000F));
	    System.out.println("Packettype value: " + packetType);
	    switch (packetType) {
	    case 0:
		// forbidden
		throw new ForbiddenPacketTypeException("PacketTye of 0 is not allowed.");
	    case 1:
		// CONNECT connect request from client to server
		if (flags == 0) {
		    remainingBytes = determineRemainingBytes(theReadBytes, 1);
		    determineRemainingLengthValue(mqttMessage);
		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 2:
		// CONNACK connect acknowledgment can't be received because we
		// are the server...
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 3:
		// PUBLISH publish message from client to server
		if ((flags & 0x1) == 1) {
		    retain = true;
		}
		if ((flags & 0x2) == 1) {
		    qos1 = true;
		}
		if ((flags & 0x4) == 1) {
		    qos2 = true;
		}
		if ((flags & 0x8) == 1) {
		    dup = true;
		}
		break;
	    case 4:
		// PUBACK publish acknowledgment from client to server or v.v.
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 5:
		// PUBREC publish received assured delivery part 1 (both ways)
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 6:
		// PUBREL publish release assured delivery part 2 (both ways)
		if (flags == 2) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 7:
		// PUBCOMP publish complete assured delivery part 1 (both ways)
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 8:
		// SUBSCRIBE subscribe client subscribe request
		if (flags == 2) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 9:
		// SUBACK Subscribe acknowledge from server to client. Can't be
		// received as we are the server
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 10:
		// // UNSUBSCRIBE unsubscribe client subscribe request
		if (flags == 2) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 11:
		// UNSUBACK Unsubscribe acknowledge from server to client. Can't
		// be received as we are the server
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 12:
		// PINGREQ ping request client to server
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 13:
		// PINGRESP Ping response server to client (Can't be received as
		// we are the server
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 14:
		// // DISCONNECT Client disconnects from server
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 15:
		// Reserved Forbidden.
		throw new ForbiddenPacketTypeException("PacketTye of 15 is not allowed.");
	    }

	}

    }

    public MqttMessage determineRemainingLengthValue(MqttMessage mqttMessage) {
	byte[] remainingBytes = mqttMessage.getRemainingBytes();
	int multiplier = 0x01;
	int value = 0;
	int index = 0;
	int encodedByte = 0;
	do {
	    encodedByte = remainingBytes[index] & 0xFF;
	    value += (encodedByte & 127) * multiplier;
	    multiplier *= 128;
	    if (multiplier > 128 * 128 * 128 || index > 3) {
		System.out.println("Malformed Remaining Length");
	    }
	    index++;
	} while ((encodedByte & 128) != 0 & index <= 3);
	mqttMessage.setRemainingNumberOfBytes(value);
	mqttMessage.setLengthBytes(index);
	return mqttMessage;
    }

    private byte[] determineRemainingBytes(byte[] dinges, int startNumber) {
	byte[] theResult = new byte[dinges.length - 1];
	for (int index = startNumber; index < dinges.length; index++) {
	    theResult[index - startNumber] = dinges[index];
	}
	return theResult;
    }
}