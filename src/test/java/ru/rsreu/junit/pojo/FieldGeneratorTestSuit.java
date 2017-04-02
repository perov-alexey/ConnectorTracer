package ru.rsreu.junit.pojo;

import org.junit.Test;
import ru.rsreu.tracer.pojo.Channel;
import ru.rsreu.tracer.pojo.Connector;
import ru.rsreu.tracer.pojo.Field;
import ru.rsreu.tracer.pojo.Link;
import ru.rsreu.tracer.utils.FieldGenerator;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class contains tests for field generator methods
 */
public class FieldGeneratorTestSuit {

    /**
     * This test checks field generation correctness
     */
    @Test
    public void testFieldGeneration() {
        FieldGenerator generator = new FieldGenerator();
        Field field = generator.generateField(5, 4, 2, 3, 2, 10);
        assertEquals("Wrong amount of generated connectors", 5, field.getConnectors().size());
        assertEquals("Wrong amount of generated links", 3, field.getLinks().size());
    }

    /**
     * This test checks throwing of the IllegalArgumentException if you try to create connector with zero pins
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAmountOfLinksWithoutPins() {
        FieldGenerator generator = new FieldGenerator();
        generator.generateField(5, 0, 2, 3, 2, 10);
    }

    /**
     * This test checks connector generating correctness
     */
    @Test
    public void testConnectorCreation() {
        FieldGenerator generator = new FieldGenerator();

        int pinsAmount = 3;
        int pinColumnsAmount = 2;

        Connector connector = generator.generateConnector(null, pinsAmount, pinColumnsAmount, 2, 10);
        assertEquals("Wrong amount of generated pins", pinsAmount * pinColumnsAmount, connector.getPins().size());
        assertNotNull("Top channel wasn't created", connector.getTopChannel());
        assertNotNull("Bottom channel wasn't created", connector.getBottomChannel());
    }

    /**
     * This test checks channel creation correctness
     */
    @Test
    public void testChannelCreation() {
        int minChannelCapacity = 2;
        int maxChannelCapacity = 10;

        FieldGenerator generator = new FieldGenerator();
        Channel channel = generator.generateChannel(null, true, minChannelCapacity, maxChannelCapacity);
        assertTrue("Wrong channel capacity",
                channel.getMaxCapacity() >= minChannelCapacity
                        && channel.getMaxCapacity() <= maxChannelCapacity);
        assertTrue("Generated channel must be top channel", channel.isTop());
    }

    /**
     * This test checks link generation correctness
     */
    @Test
    public void testLinkCreation() {
        FieldGenerator generator = new FieldGenerator();

        int pinsAmount = 3;
        int pinColumnsAmount = 2;
        int minChannelCapacity = 2;
        int maxChannelCapacity = 10;

        Connector firstConnector = generator.generateConnector(null, pinsAmount, pinColumnsAmount, minChannelCapacity, maxChannelCapacity);
        Connector secondConnector = generator.generateConnector(firstConnector, pinsAmount, pinColumnsAmount, minChannelCapacity, maxChannelCapacity);
        Connector thirdConnector = generator.generateConnector(secondConnector, pinsAmount, pinColumnsAmount, minChannelCapacity, maxChannelCapacity);
        Connector forthConnector = generator.generateConnector(thirdConnector, pinsAmount, pinColumnsAmount, minChannelCapacity, maxChannelCapacity);

        List<Connector> connectors = Arrays.asList(firstConnector, secondConnector, thirdConnector, forthConnector);

        Link link = generator.generateLink(connectors);

        assertNotEquals("Link's pins must be placed in the different connectors",
                link.getFirstPin().getContainer(), link.getSecondPin().getContainer());
    }
}
