package nl.karnhuis.mqttserver.exceptions;

public class ForbiddenPacketTypeException extends Exception {
	 
    /**
    * Generated serial ID.
    */
    private static final long serialVersionUID = -5904031915083203862L;
   
    public ForbiddenPacketTypeException(String message) {
          super(message);
    }

}