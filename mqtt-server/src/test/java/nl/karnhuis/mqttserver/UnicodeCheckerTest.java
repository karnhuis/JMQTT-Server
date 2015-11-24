package nl.karnhuis.mqttserver;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnicodeCheckerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// This test will check the given string to see if it contains invalid UTF-8 characters.
	// The following characters are not allowed: D800...DFFF
	@Test
	public void correctStringTest() {
		String someText = "Hello World";
		assertEquals(true, UnicodeChecker.isStringValid(someText));
		someText = "\uE000 Hello World";
		assertEquals(true, UnicodeChecker.isStringValid(someText));
		someText = "\uD799 Hello World";
		assertEquals(true, UnicodeChecker.isStringValid(someText));
		someText = "\u002C Hello World";
		assertEquals(true, UnicodeChecker.isStringValid(someText));
	}
	
	// This test will check the given string to see if it contains invalid UTF-8 characters.
	// The following characters are not allowed: D800...DFFF
	@Test
	public void wrongStringTest() {
		String someText = "Hello World \uD800";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "Hello World \uDFFF";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "Hello World \uD845";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
	}
	
	// This test will check the given string to see if it contains invalid UTF-8 characters.
	// The NULL string \u0000 is not allowed anywhere in the string
	@Test
	public void nullStringTest() {
		String someText = "Hello World \u0000";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "\u0000 Hello World";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "Hello \u0000World";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
	}
	
	// This test will check the given string to see if it contains invalid UTF-8 characters.
	// The following characters are not allowed: 0001...001F and 007F...009F
	@Test
	public void controlCharactersStringTest() {
		String someText = "Hello World \u0001";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "\u001F Hello World";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "Hello \u0009World";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "Hello World \u007F";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "\u008B Hello World";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
		someText = "Hello \u009FWorld";
		assertEquals(false, UnicodeChecker.isStringValid(someText));
	}
	
	// This test will check the given string to see if it contains invalid UTF-8 characters.
	// The undefined Unicode characters are not allowed.
	// the defined byte array represent the string "Hello World " with at the end an undefined 
	// UTF-8 Unicode character represented by 4 bytes with given values. 
	// It is not possible to represent these as hex values as Java things I try to use int values.
	// Need to convert them to decimal values then it seems to be working.
	@Test
	public void undefinedCharactersStringTest() {
		byte[] utf8AsByteArray = {0x00,0x48,0x00,0x65,0x00,0x6C,0x00,0x6C,0x00,0x6F,0x00,0x20,
				0x00,0x57,0x00,0x6F,0x00,0x72,0x00,0x6C,0x00,0x64,-16,-125,-116,-120};
		String someText = new String(utf8AsByteArray, Charset.forName("UTF-8"));
		assertEquals(false, UnicodeChecker.isStringValid(someText));
	}
	
	// This test will check to see if the given string contains a NoSpace No Break sequence of 
	// characters. Such a sequence might not be removed during transport of this byte array.
	@Test
	public void detectNoSpaceNoBreakSequence() {
		byte[] utf8AsByteArray = {0x00,0x48,0x00,0x65,0x00,0x6C,0x00,0x6C,0x00,0x6F,
				0x00,0x20,0x00,0x57,0x00,0x6F,0x00,0x72,0x00,0x6C,0x00,0x64,-17,-69,-65};
		String someText = new String(utf8AsByteArray, Charset.forName("UTF-8"));
		assertEquals(true, UnicodeChecker.containsNoSpaceNoBreakSequence(someText) 
				|| UnicodeChecker.containsNoSpaceNoBreakSequenceUnicode(someText));
	}
	
}
