/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public class ConnectMessage extends Message {

    public ConnectMessage(byte[] message) {
	setMessageAsBytes(message);
    }

    public void handleMessage() {

    }
}
