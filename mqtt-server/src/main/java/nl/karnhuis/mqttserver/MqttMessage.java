/**
 * 
 */
package nl.karnhuis.mqttserver;

/**
 * @author werner
 *
 */
public class MqttMessage {

    private boolean retain = false;
    private boolean dup = false;
    private boolean qos1 = false;
    private boolean qos2 = false;

    private byte[] message;
    private int messageLength = 1;
    private int numberOfLenghtBytes = 1;

    public void setRemainingNumberOfBytes(int value) {
	messageLength = value;
    }

    public void setLengthBytes(int numberOfBytes) {
	numberOfLenghtBytes = numberOfBytes;
    }

    public byte[] getRemainingBytes() {
	return message;
    }

    public void setMessage(byte[] theReadBytes) {
	message = theReadBytes;
    }

}
