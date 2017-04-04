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
     * This test checks field generator behavior about link generation. Early it never create link to last connector.
     */
    @Test
    public void testAbilityToCreateFieldWithLinkToLastChannel() {
        FieldGenerator generator = new FieldGenerator();
        boolean isLinkToLastConnectorCreated = false;
        int connectorsAmount = 4;

        for (int i = 0; i < 1000; i++) {
            Field field = generator.generateField(connectorsAmount, 3, 2,
                    connectorsAmount * 2, 1, 4);
            Connector lastConnector = field.getConnectors().get(field.getConnectors().size() - 1);
            for (Link link : field.getLinks()) {
                if (link.getFirstPin().getContainer().equals(lastConnector) || link.getSecondPin().getContainer().
                        equals(lastConnector)) {
                    isLinkToLastConnectorCreated = true;
                    break;
                }
            }
            if (isLinkToLastConnectorCreated) break;
        }
        assertTrue("Link never creates with last field connector", isLinkToLastConnectorCreated);
    }

    /**
     * This test checks correctness of the capacity upper bound of the generated channel
     */
    @Test
    public void testChannelCapacityUpperBoundCorrectness() {
        FieldGenerator generator = new FieldGenerator();
        boolean isUpperBoundPresent = false;
        int upperBound = 2;
        for (int i = 0; i < 1000; i++) {
            if (generator.generateChannel(null, true, 0, upperBound).getMaxCapacity() == upperBound) {
                isUpperBoundPresent = true;
                break;
            }
        }
        assertTrue("Channel upper bound is wrong", isUpperBoundPresent);
    }

    /**
     * This test creates channel with constant capacity. Early it raise exception.
     */
    @Test
    public void testChannelGenerationWithConstantCapacity() {
        FieldGenerator generator = new FieldGenerator();
        Channel channel = generator.generateChannel(null, true, 1, 1);
        assertEquals("Generated channel have wrong capacity", 1, channel.getMaxCapacity());
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
