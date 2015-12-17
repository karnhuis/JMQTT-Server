package nl.karnhuis.mqttserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import nl.karnhuis.mqttserver.messagetypes.MqttMessage;

public class ServerThread extends Thread {
    private Socket socket = null;
    private List<MqttListener> theListeners = new ArrayList<MqttListener>();

    public ServerThread(Socket socket) {
	super("ServerThread");
	this.socket = socket;
    }

    public void addListener(MqttListener toAdd) {
	theListeners.add(toAdd);
    }

    public void removeListener(MqttListener toRemove) {
	theListeners.remove(theListeners.indexOf(toRemove));
    }

    public void notify(String message) {
	for (MqttListener aListener : theListeners) {
	    aListener.handleMessage(message);
	}
    }

    public void run() {

	try (OutputStream out = socket.getOutputStream(); InputStream in = socket.getInputStream();) {
	    byte[] readBytes = new byte[1024];
	    while (in.read(readBytes, 0, readBytes.length) != -1) {
		MqttMessage mqttMessage = new MqttMessage(readBytes);
		mqttMessage.handleMessage();
	    }
	    // use the out to send byte array if we need to tell something to
	    // the world
	    // use the in to receive incoming bytes from the outside world

	    // String inputLine, outputLine;
	    // KnockKnockProtocol kkp = new KnockKnockProtocol();
	    // outputLine = kkp.processInput(null);
	    // out.println(outputLine);
	    //
	    // while ((inputLine = in.readLine()) != null) {
	    // outputLine = kkp.processInput(inputLine);
	    // out.println(outputLine);
	    // if (outputLine.equals("Bye"))
	    // break;
	    // }
	    socket.close();
	} catch (IOException e) {
	    System.out.println("Problem communicating with the outside world.");
	    System.exit(6);
	}
    }

    public void handleBytes(byte[] theReadBytes) {

    }
}