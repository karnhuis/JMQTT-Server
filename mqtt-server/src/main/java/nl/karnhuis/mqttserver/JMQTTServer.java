/**
 * 
 */
package nl.karnhuis.mqttserver;

/**
 * @author werner
 *
 */
public class JMQTTServer {

	/** 
	 * Default constructor
	 * 
	 */
	public JMQTTServer() {
		// read the properties file that contain IP-address and port number
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Need to start several threads here:
		// - One for receiving messages, 
		// - One for sending messages and
		// - One for handling other stuff.
		// As we need this to be a server we need to 
		// open a socket and listen for incoming and 
		// send outgoing messages.
		
	}

}
