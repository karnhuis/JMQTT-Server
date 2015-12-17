/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public class PinReqMessage extends Message {

    public PinReqMessage(byte[] remainingBytes) {
	setMessageAsBytes(remainingBytes);
    }

    @Override
    public void handleMessage() {
	// TODO Auto-generated method stub

    }

}
