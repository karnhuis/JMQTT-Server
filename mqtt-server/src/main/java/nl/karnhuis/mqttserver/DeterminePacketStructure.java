package nl.karnhuis.mqttserver;

import nl.karnhuis.mqttserver.exceptions.ForbiddenPacketTypeException;
import nl.karnhuis.mqttserver.messagetypes.ConnackMessage;
import nl.karnhuis.mqttserver.messagetypes.ConnectMessage;
import nl.karnhuis.mqttserver.messagetypes.DisconnectMessage;
import nl.karnhuis.mqttserver.messagetypes.MqttMessage;
import nl.karnhuis.mqttserver.messagetypes.PinReqMessage;
import nl.karnhuis.mqttserver.messagetypes.PingRespMessage;
import nl.karnhuis.mqttserver.messagetypes.PubAckMessage;
import nl.karnhuis.mqttserver.messagetypes.PubCompMessage;
import nl.karnhuis.mqttserver.messagetypes.PubRecMessage;
import nl.karnhuis.mqttserver.messagetypes.PubRelMessage;
import nl.karnhuis.mqttserver.messagetypes.PublishMessage;
import nl.karnhuis.mqttserver.messagetypes.SubAckMessage;
import nl.karnhuis.mqttserver.messagetypes.SubScribeMessage;
import nl.karnhuis.mqttserver.messagetypes.UnSubAckMessage;
import nl.karnhuis.mqttserver.messagetypes.UnsubscribeMessage;

public class DeterminePacketStructure {

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

	MqttMessage mqttMessage = new MqttMessage(theReadBytes);
	mqttMessage.handleMessage();
	for (int index = 0; index < theReadBytes.length; index++) {
	    byte packetType = (byte) ((theReadBytes[0] & 0x00F0) >> 4);
	    byte flags = (byte) ((theReadBytes[0] & 0x000F));
	    System.out.println("Packettype value: " + packetType);
	    switch (mqttMessage.getMessageType()) {
	    case 0:
		// forbidden
		throw new ForbiddenPacketTypeException("PacketTye of 0 is not allowed.");
	    case 1:
		// CONNECT connect request from client to server
		ConnectMessage connect = new ConnectMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 2:
		// CONNACK connect acknowledgment can't be received because we
		// are the server...
		ConnackMessage connack = new ConnackMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 3:
		// PUBLISH publish message from client to server
		PublishMessage publish = new PublishMessage(mqttMessage.getRemainingBytes());
		break;
	    case 4:
		// PUBACK publish acknowledgment from client to server or v.v.
		PubAckMessage puback = new PubAckMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 5:
		// PUBREC publish received assured delivery part 1 (both ways)
		PubRecMessage pubrec = new PubRecMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 6:
		// PUBREL publish release assured delivery part 2 (both ways)
		PubRelMessage pubrel = new PubRelMessage(mqttMessage.getRemainingBytes());
		if (flags == 2) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 7:
		// PUBCOMP publish complete assured delivery part 1 (both ways)
		PubCompMessage pubcomp = new PubCompMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 8:
		// SUBSCRIBE subscribe client subscribe request
		SubScribeMessage subscribe = new SubScribeMessage(mqttMessage.getRemainingBytes());
		if (flags == 2) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 9:
		// SUBACK Subscribe acknowledge from server to client. Can't be
		SubAckMessage suback = new SubAckMessage(mqttMessage.getRemainingBytes());
		// received as we are the server
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 10:
		// // UNSUBSCRIBE unsubscribe client subscribe request
		UnsubscribeMessage unsubscribe = new UnsubscribeMessage(mqttMessage.getRemainingBytes());
		if (flags == 2) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 11:
		// UNSUBACK Unsubscribe acknowledge from server to client. Can't
		// be received as we are the server
		UnSubAckMessage unsuback = new UnSubAckMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 12:
		// PINGREQ ping request client to server
		PinReqMessage pinreq = new PinReqMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 13:
		// PINGRESP Ping response server to client (Can't be received as
		// we are the server
		PingRespMessage pingresp = new PingRespMessage(mqttMessage.getRemainingBytes());
		if (flags == 0) {

		} else {
		    // This is wrong. Can't be happening.
		}
		break;
	    case 14:
		// // DISCONNECT Client disconnects from server
		DisconnectMessage disconnect = new DisconnectMessage(mqttMessage.getRemainingBytes());
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
}