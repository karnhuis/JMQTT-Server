/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public class PubCompMessage extends Message {

    public PubCompMessage(byte[] remainingBytes) {
	setMessageAsBytes(remainingBytes);
    }

    @Override
    public void handleMessage() {
	// TODO Auto-generated method stub

    }

}
