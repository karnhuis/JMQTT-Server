package nl.karnhuis.mqttserver;

import org.apache.log4j.Logger;

/**
 * This helper class will perform Unicode checks. The checks are taken from the 
 * MQTT Version 3.1.1 specifications of the OASIS Standard
 * 
 * @author werner
 *
 */
public class UnicodeChecker {
	
	private static Logger log = Logger.getLogger("VeldPlanner");

	/**
	 * This method will test the given string to see if it contains invalid UTF-8 characters.
	 * Invalid characters are:
	 * All characters from 0xD800 until 0xDFFF including ([MQTT-1.5.3-1])
	 * All characters from 0x0001 until 0x001F including
	 * All characters from 0x007F until 0x009F including
	 * All undefined Unicode code points
	 * The \u0000 character ([MQTT-1.5.3-2])
	 * 
	 * @param textToExamine The text to examine.
	 * @return true only if none of the illegal characters are found
	 */
	public static boolean isStringValid(String textToExamine) {
		for(int index = 0; index < textToExamine.length(); index++) {
			if(textToExamine.charAt(index) >= '\uD800' && textToExamine.charAt(index) <= '\uDFFF') {
				return false;
			}
			if(textToExamine.charAt(index) == '\u0000') {
				return false;
			}
			if(textToExamine.charAt(index) >= '\u0001' && textToExamine.charAt(index) <= '\u001F') {
				return false;
			}
			if(textToExamine.charAt(index) >= '\u007F' && textToExamine.charAt(index) <= '\u009F') {
				return false;
			}
			if(!Character.isDefined(textToExamine.codePointAt(index))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method will check to see if there is a No Space No Break sequence of characters in 
	 * this string. If so then it should be replaced by a specific unicode character but that 
	 * is the job of another method.
	 * 
	 * @param someText The text to be examined.
	 * @return true only of the following byte value sequence is found: 0xEF, 0xBB, 0xBF
	 */
	public static boolean containsNoSpaceNoBreakSequence(String textToExamine) {
		System.out.println(textToExamine);
		System.out.println(escapeNonAscii(textToExamine));
		for(int index = 0; index < textToExamine.length(); index++) {
			if(index < textToExamine.length() - 2
					&& textToExamine.charAt(index) == 0xEF 
					&& textToExamine.charAt(index+1) == 0xBB
					&& textToExamine.charAt(index+2) == 0xBF) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsNoSpaceNoBreakSequenceUnicode(String textToExamine) {
		for(int index = 0; index < textToExamine.length(); index++) {
			if(textToExamine.charAt(index) >= '\uFEFF') {
				return true;
			}
		}
		return false;
	}
	
	private static String escapeNonAscii(String str) {

		  StringBuilder retStr = new StringBuilder();
		  for(int i=0; i<str.length(); i++) {
		    int cp = Character.codePointAt(str, i);
		    int charCount = Character.charCount(cp);
		    if (charCount > 1) {
		      i += charCount - 1; // 2.
		      if (i >= str.length()) {
		        throw new IllegalArgumentException("truncated unexpectedly");
		      }
		    }

		    if (cp < 128) {
		      retStr.appendCodePoint(cp);
		    } else {
		      retStr.append(String.format("\\u%x", cp));
		    }
		  }
		  return retStr.toString();
		}
}
