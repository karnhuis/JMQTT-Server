package nl.karnhuis.mqttserver;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RemainingLengthInfoTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // This test will check the given string to see if it contains invalid UTF-8
    // characters.
    // The following characters are not allowed: D800...DFFF
    @Test
    public void CorrectLengthFields64() {
	RemainingLengthInfo rli = new RemainingLengthInfo();
	byte[] correct = { 0x40, (byte) 0x80, (byte) 0x80, 0x03 };
	rli.setTheLengthBytes(correct);
	assertEquals(64, rli.getRemainingNumberOfBytes());
    }

    @Test
    public void CorrectLengthFields321() {
	RemainingLengthInfo rli = new RemainingLengthInfo();
	byte[] correct = { (byte) 193, (byte) 0x02, 0x41, 0x03 };
	rli.setTheLengthBytes(correct);
	assertEquals(321, rli.getRemainingNumberOfBytes());
    }

}
