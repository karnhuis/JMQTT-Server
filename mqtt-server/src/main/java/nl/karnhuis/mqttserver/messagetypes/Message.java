/**
 * 
 */
package nl.karnhuis.mqttserver.messagetypes;

/**
 * @author werner
 *
 */
public abstract class Message {

    protected byte[] messageAsBytes;

    public void setMessageAsBytes(byte[] messageAsBytes) {
	this.messageAsBytes = messageAsBytes;
    }

    public abstract void handleMessage();

}
