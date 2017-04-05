package ru.rsreu.junit.pojo;

import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.pojo.Trace;

import static org.junit.Assert.assertEquals;

/**
 * Test suit for Trace class
 */
public class TraceTestSuit {

    /**
     * Test trace length calculation functional
     */
    @Test
    public void testLengthCalculation() {
        Trace topTrace = FixtureProvider.getTopTrace();
        Trace bottomTrace = FixtureProvider.getBottomTrace();

        assertEquals("Top trace length was calculated wrong", 450, topTrace.getLength());
        assertEquals("Bottom trace length was calculated wrong", 430, bottomTrace.getLength());
    }

}
