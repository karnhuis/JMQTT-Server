/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public class ConnackMessage extends Message {

    public ConnackMessage(byte[] remainingBytes) {
	setMessageAsBytes(remainingBytes);
    }

    @Override
    public void handleMessage() {
	// TODO Auto-generated method stub

    }

}
