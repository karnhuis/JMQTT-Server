/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public class PublishMessage extends Message {

    public PublishMessage(byte[] remainingBytes) {
	setMessageAsBytes(remainingBytes);
    }

    @Override
    public void handleMessage() {
	// TODO Auto-generated method stub

    }

}
