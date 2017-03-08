package ru.rsreu.junit.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.pojo.Connector;
import ru.rsreu.tracer.pojo.Field;
import ru.rsreu.tracer.pojo.Link;
import ru.rsreu.tracer.pojo.Trace;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test suit for Field class
 */
public class FieldSuit {

    /**
     * Test rating calculation field functional
     */
    @Test
    public void testRatingCalculation() {
        Field fixture = FixtureProvider.getTracedField();
        assertEquals("Field rating was calculated wrong", 1610, fixture.getRating());
    }

    /**
     * Test link tracing functional
     */
    @Test
    public void testLinkTracing() {
        Field fixture = FixtureProvider.getTopChannelOverloadedField();

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("f://test.json"), fixture);
        } catch (IOException e) {
            e.printStackTrace();
        }


        fixture.traceLink(fixture.getLinks().get(0), true);
        fixture.traceLink(fixture.getLinks().get(1), true);
        fixture.traceLink(fixture.getLinks().get(2), true);
        fixture.traceLink(fixture.getLinks().get(3), false);

        assertEquals("First top channel occupancy is wrong",
                0, fixture.getConnectors().get(0).getTopChannel().getOccupancy());
        assertEquals("First bottom channel occupancy is wrong",
                0, fixture.getConnectors().get(0).getBottomChannel().getOccupancy());
        assertEquals("Second top channel occupancy is wrong",
                1, fixture.getConnectors().get(1).getTopChannel().getOccupancy());
        assertEquals("Second bottom channel occupancy is wrong",
                0, fixture.getConnectors().get(1).getBottomChannel().getOccupancy());
        assertEquals("Third top channel occupancy is wrong",
                2, fixture.getConnectors().get(2).getTopChannel().getOccupancy());
        assertEquals("Third bottom channel occupancy is wrong",
                1, fixture.getConnectors().get(2).getBottomChannel().getOccupancy());
        assertEquals("Fourth top channel occupancy is wrong",
                0, fixture.getConnectors().get(3).getTopChannel().getOccupancy());
        assertEquals("Fourth bottom channel occupancy is wrong",
                0, fixture.getConnectors().get(3).getBottomChannel().getOccupancy());
    }

    /**
     * Test getting in-between connectors functional
     */
    @Test
    public void testGettingConnectorsBetween() {
        Field fixture = FixtureProvider.getTopChannelOverloadedField();
        List<Connector> connectors = fixture.getConnectors();

        List<Connector> betweenConnectors = fixture.getConnectorsBetween(connectors.get(0), connectors.get(3));

        assertEquals("Method return wrong connectors", Arrays.asList(connectors.get(1), connectors.get(2)), betweenConnectors);
    }

    /**
     * Test field's functionality of solution's acceptance.
     */
    @Test
    public void testFieldAcceptableChecking() {
        Field field = FixtureProvider.getTopChannelOverloadedField();
        for (Link link : field.getLinks()) {
            field.traceLink(link, true);
        }
        assertFalse("This field is not acceptable solution", field.isAcceptableField());

        field = FixtureProvider.getTopChannelOverloadedField();
        field.traceLink(field.getLinks().get(0), true);
        field.traceLink(field.getLinks().get(1), true);
        field.traceLink(field.getLinks().get(2), true);
        field.traceLink(field.getLinks().get(3), false);
        assertTrue("This field is acceptable solution", field.isAcceptableField());
    }

    /**
     * Test field's retrace functionality
     */
    @Test
    public void testRetrace() {
        Field field = FixtureProvider.getTopChannelOverloadedField();
        Trace trace = field.traceLink(field.getLinks().get(0), true);

        assertTrue("Trace must be traced by top", trace.getPath().get(0).isTop());

        trace = field.retrace(trace, false);

        assertEquals("Wrong amount of traced links", 1, field.getTraces().size());
        assertFalse("Trace must be traced by bottom", trace.getPath().get(0).isTop());
    }

}
