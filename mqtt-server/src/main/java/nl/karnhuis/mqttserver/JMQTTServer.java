/**
 * 
 */
package nl.karnhuis.mqttserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

/**
 * @author werner
 *
 */
public class JMQTTServer implements MqttListener{

		private static Properties props = new Properties();
		
		static {
			try {
				props.load(new FileInputStream("jMQTT.props"));
			} catch (FileNotFoundException e) {
				System.out.println("The properties file called: \"jMQTT.props\" was not found");
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Unable to read properties file.");
				System.exit(2);
			}
		}	
	
	/** 
	 * Default constructor
	 * 
	 */
	public JMQTTServer() {
		// Do something useful
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JMQTTServer theServer = new JMQTTServer();
		boolean listening = true;
		
		try (ServerSocket sock = new ServerSocket(Integer.parseInt(props.getProperty("port")))){
            while (listening) {
                ServerThread st = new ServerThread(sock.accept());
                st.addListener(theServer);
                // Maak een eventlistener aan zodat we naar events van deze thread kunnen luisteren
                // Zet deze thread in een event listener zodat er events door opgevangen en afgehandeld kunnen worden.
                st.start();
            }
		} catch (NumberFormatException e) {
			System.out.println("Port contains invaled characters: " + props.getProperty("port"));
			System.exit(3);
		} catch (IOException e) {
			System.out.println("Unable to communnicate over network connection.");
			System.exit(4);
		}
	}

	@Override
	public void handleMessage(String message) {
		// TODO Auto-generated method stub
		
	}

}
