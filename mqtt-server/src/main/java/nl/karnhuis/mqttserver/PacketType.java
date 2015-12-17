/**
 * 
 */
package nl.karnhuis.mqttserver;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author werner
 *
 */
public enum PacketType {

    UNKNOWN(0), CONNECT(1), CONNACK(2), PUBLISH(3), PUBACK(4), PUBREC(5), PUBREL(6), PUBCOMP(7), SUBSCRIBE(8), SUBACK(
	    9), UNSUBSCRIBE(10), UNSUBACK(11), PINGREQ(12), PINGRESP(13), DISCONNECT(14), FORBIDDEN(15);

    private int type = 0;

    // Lookup table
    private static final Map<Integer, PacketType> lookup = new HashMap<Integer, PacketType>();

    static {
	for (PacketType pt : EnumSet.allOf(PacketType.class))
	    lookup.put(pt.getType(), pt);
    }

    private PacketType(int type) {
	this.type = type;
    }

    public int getType() {
	return type;
    }

    public static PacketType getType(int i) {
	return lookup.get(i);
    }

}
