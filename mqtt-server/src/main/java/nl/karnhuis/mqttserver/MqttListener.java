package nl.karnhuis.mqttserver;

public interface MqttListener {

	public void handleMessage(String message);
}
