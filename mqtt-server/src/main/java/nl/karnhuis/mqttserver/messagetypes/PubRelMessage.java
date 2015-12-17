/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public class PubRelMessage extends Message {

    public PubRelMessage(byte[] remainingBytes) {
	setMessageAsBytes(remainingBytes);
    }

    @Override
    public void handleMessage() {
	// TODO Auto-generated method stub

    }
}
