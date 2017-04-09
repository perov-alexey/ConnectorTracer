package ru.rsreu.junit.pojo;

import org.junit.Test;
import ru.rsreu.junit.FixtureProvider;
import ru.rsreu.tracer.pojo.Link;

import static org.junit.Assert.assertEquals;

/**
 * Test suit for Link class
 */
public class LinkTestSuit {

    /**
     * Test link length calculation functional
     */
    @Test
    public void testLengthCalculation() {
        Link link = FixtureProvider.getTopChannelOverloadedField().getLinks().get(0);
        assertEquals("Wrong link's length calculation (traced by top)", 450, link.getLength(true));
        assertEquals("Wrong link's length calculation (traced by bottom)", 430, link.getLength(false));
    }

}
