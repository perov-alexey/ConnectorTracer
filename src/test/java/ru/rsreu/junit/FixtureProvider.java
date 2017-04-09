package ru.rsreu.junit;

import ru.rsreu.tracer.pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provide fixtures for tests
 */
public class FixtureProvider {

    /**
     * Create field which contain overloaded top channel. This field used to test branch and bound algorithm
     * implementation on channel overload
     * @return Field fixture
     */
    public static Field getTopChannelOverloadedField() {
        // First connector
        Pin firstConnectorFirstPin = new Pin(50, 30);
        List<Pin> firstConnectorPins = Arrays.asList(firstConnectorFirstPin);
        Channel firstConnectorTopChannel = new Channel(1, true);
        Channel firstConnectorBottomChannel = new Channel(1, false);

        Connector firstConnector = new Connector(10, 100, 200, 100, firstConnectorPins, firstConnectorTopChannel, firstConnectorBottomChannel);

        firstConnectorFirstPin.setContainer(firstConnector);
        firstConnectorTopChannel.setConnector(firstConnector);
        firstConnectorBottomChannel.setConnector(firstConnector);

        // Second connector
        Pin secondConnectorFirstPin = new Pin(50, 20);
        Pin secondConnectorSecondPin = new Pin(50, 100);
        Pin secondConnectorThirdPin = new Pin(50, 180);
        List<Pin> secondConnectorPins = Arrays.asList(secondConnectorFirstPin, secondConnectorSecondPin, secondConnectorThirdPin);
        Channel secondConnectorTopChannel = new Channel(2, true);
        Channel secondConnectorBottomChannel = new Channel(1, false);

        Connector secondConnector = new Connector(130, 100, 200, 100, secondConnectorPins, secondConnectorTopChannel, secondConnectorBottomChannel);

        secondConnectorFirstPin.setContainer(secondConnector);
        secondConnectorSecondPin.setContainer(secondConnector);
        secondConnectorThirdPin.setContainer(secondConnector);
        secondConnectorTopChannel.setConnector(secondConnector);
        secondConnectorTopChannel.setConnector(secondConnector);

        // Third connector
        Pin thirdConnectorFirstPin = new Pin(50, 180);
        List<Pin> thirdConnectorPins = Arrays.asList(thirdConnectorFirstPin);
        Channel thirdConnectorTopChannel = new Channel(2, true);
        Channel thirdConnectorBottomChannel = new Channel(2, false);

        Connector thirdConnector = new Connector(250, 100, 200, 100, thirdConnectorPins, thirdConnectorTopChannel, thirdConnectorBottomChannel);

        thirdConnectorFirstPin.setContainer(thirdConnector);
        thirdConnectorTopChannel.setConnector(thirdConnector);
        thirdConnectorBottomChannel.setConnector(thirdConnector);

        // Fourth connector
        Pin fourthConnectorFirstPin = new Pin(50, 20);
        Pin fourthConnectorSecondPin = new Pin(50, 100);
        Pin fourthConnectorThirdPin = new Pin(50, 180);
        List<Pin> fourthConnectorPins = Arrays.asList(fourthConnectorFirstPin, fourthConnectorSecondPin, fourthConnectorThirdPin);
        Channel fourthConnectorTopChannel = new Channel(1, true);
        Channel fourthConnectorBottomChannel = new Channel(1, false);

        Connector fourthConnector = new Connector(370, 100, 200, 100, fourthConnectorPins, fourthConnectorTopChannel, fourthConnectorBottomChannel);

        fourthConnectorFirstPin.setContainer(fourthConnector);
        fourthConnectorSecondPin.setContainer(fourthConnector);
        fourthConnectorThirdPin.setContainer(fourthConnector);
        fourthConnectorTopChannel.setConnector(fourthConnector);
        fourthConnectorBottomChannel.setConnector(fourthConnector);

        // Links
        Link firstLink = new Link(firstConnectorFirstPin, thirdConnectorFirstPin);
        Link secondLink = new Link(secondConnectorFirstPin, fourthConnectorFirstPin);
        Link thirdLink = new Link(secondConnectorSecondPin, fourthConnectorThirdPin);
        Link fourthLink = new Link(secondConnectorThirdPin, fourthConnectorSecondPin);

        List<Connector> connectors = Arrays.asList(firstConnector, secondConnector, thirdConnector, fourthConnector);
        List<Link> links = Arrays.asList(firstLink, secondLink, thirdLink, fourthLink);

        return new Field(connectors, links);
    }

    /**
     * Create defective field which can't be traced.
     * @return Defective field
     */
    public static Field getDefectiveField() {
        // First connector
        Pin firstConnectorFirstPin = new Pin(50, 20);
        Pin firstConnectorSecondPin = new Pin(50, 60);
        Pin firstConnectorThirdPin = new Pin(50, 100);
        List<Pin> firstConnectorPins = Arrays.asList(firstConnectorFirstPin, firstConnectorSecondPin, firstConnectorThirdPin);
        Channel firstConnectorTopChannel = new Channel(1, true);
        Channel firstConnectorBottomChannel = new Channel(1, false);

        Connector firstConnector = new Connector(10, 100, 200, 100, firstConnectorPins, firstConnectorTopChannel, firstConnectorBottomChannel);

        firstConnectorFirstPin.setContainer(firstConnector);
        firstConnectorSecondPin.setContainer(firstConnector);
        firstConnectorThirdPin.setContainer(firstConnector);
        firstConnectorTopChannel.setConnector(firstConnector);
        firstConnectorBottomChannel.setConnector(firstConnector);

        // Second connector
        Pin secondConnectorFirstPin = new Pin(50, 120);
        List<Pin> secondConnectorPins = Arrays.asList(secondConnectorFirstPin);
        Channel secondConnectorTopChannel = new Channel(1, true);
        Channel secondConnectorBottomChannel = new Channel(1, false);

        Connector secondConnector = new Connector(130, 100, 200, 100, secondConnectorPins, secondConnectorTopChannel, secondConnectorBottomChannel);

        secondConnectorFirstPin.setContainer(secondConnector);
        secondConnectorTopChannel.setConnector(secondConnector);
        secondConnectorTopChannel.setConnector(secondConnector);

        // Third connector
        Pin thirdConnectorFirstPin = new Pin(50, 140);
        List<Pin> thirdConnectorPins = Arrays.asList(thirdConnectorFirstPin);
        Channel thirdConnectorTopChannel = new Channel(2, true);
        Channel thirdConnectorBottomChannel = new Channel(2, false);

        Connector thirdConnector = new Connector(250, 100, 200, 100, thirdConnectorPins, thirdConnectorTopChannel, thirdConnectorBottomChannel);

        thirdConnectorFirstPin.setContainer(thirdConnector);
        thirdConnectorTopChannel.setConnector(thirdConnector);
        thirdConnectorBottomChannel.setConnector(thirdConnector);

        // Fourth connector
        Pin fourthConnectorFirstPin = new Pin(50, 20);
        Pin fourthConnectorSecondPin = new Pin(50, 40);
        Pin fourthConnectorThirdPin = new Pin(50, 120);
        List<Pin> fourthConnectorPins = Arrays.asList(fourthConnectorFirstPin, fourthConnectorSecondPin, fourthConnectorThirdPin);
        Channel fourthConnectorTopChannel = new Channel(1, true);
        Channel fourthConnectorBottomChannel = new Channel(1, false);

        Connector fourthConnector = new Connector(370, 100, 200, 100, fourthConnectorPins, fourthConnectorTopChannel, fourthConnectorBottomChannel);

        fourthConnectorFirstPin.setContainer(fourthConnector);
        fourthConnectorSecondPin.setContainer(fourthConnector);
        fourthConnectorThirdPin.setContainer(fourthConnector);
        fourthConnectorTopChannel.setConnector(fourthConnector);
        fourthConnectorBottomChannel.setConnector(fourthConnector);

        // Links
        Link firstLink = new Link(firstConnectorFirstPin, fourthConnectorSecondPin);
        Link secondLink = new Link(firstConnectorSecondPin, thirdConnectorFirstPin);
        Link thirdLink = new Link(firstConnectorThirdPin, fourthConnectorThirdPin);
        Link fourthLink = new Link(secondConnectorFirstPin, fourthConnectorFirstPin);

        List<Connector> connectors = Arrays.asList(firstConnector, secondConnector, thirdConnector, fourthConnector);
        List<Link> links = Arrays.asList(firstLink, secondLink, thirdLink, fourthLink);

        return new Field(connectors, links);
    }

    /**
     * Create traced field
     * @return Traced field
     */
    public static Field getTracedField() {
        Field field = getTopChannelOverloadedField();
        field.traceLink(field.getLinks().get(0), true);
        field.traceLink(field.getLinks().get(1), true);
        field.traceLink(field.getLinks().get(2), true);
        field.traceLink(field.getLinks().get(3), false);
        return field;
    }

    /**
     * Create trace placed in top channels
     * @return Trace placed in top channels
     */
    public static Trace getTopTrace() {
        Field simpleField = getTopChannelOverloadedField();
        return simpleField.traceLink(simpleField.getLinks().get(0), true);
    }

    /**
     * Create trace placed in bottom channels
     * @return Trace placed in bottom channels
     */
    public static Trace getBottomTrace() {
        Field simpleField = getTopChannelOverloadedField();
        return simpleField.traceLink(simpleField.getLinks().get(0), false);
    }

    /**
     * Create field for branch and bound handle calculation. I'm already build tree on paper for it field and I need it
     * only for using in diploma work
     * @return Modified top channel overloaded field
     */
    public static Field getFieldForBranchAndBoundCheck() {
        Field field = getTopChannelOverloadedField();

        // Fifth connector
        Pin fifthConnectorFirstPin = new Pin(50, 190);
        List<Pin> fifthConnectorPins = Arrays.asList(fifthConnectorFirstPin);
        Channel fifthConnectorTopChannel = new Channel(1, true);
        Channel fifthConnectorBottomChannel = new Channel(1, false);

        Connector fifthConnector = new Connector(490, 100, 200, 100, fifthConnectorPins, fifthConnectorTopChannel, fifthConnectorBottomChannel);

        fifthConnectorFirstPin.setContainer(fifthConnector);
        fifthConnectorTopChannel.setConnector(fifthConnector);
        fifthConnectorBottomChannel.setConnector(fifthConnector);


        // Add new pin in the first channel
        Connector firstConnector = field.getConnectors().get(0);
        Pin newPin = new Pin(50, 190, firstConnector);
        ArrayList<Pin> pins = new ArrayList();
        pins.addAll(firstConnector.getPins());
        pins.add(newPin);

        firstConnector.setPins(pins);

        // Fifth link
        Link newLink = new Link(newPin, fifthConnectorFirstPin);

        ArrayList<Link> links = new ArrayList<>();
        links.addAll(field.getLinks());
        links.add(newLink);

        field.setLinks(links);


        ArrayList<Connector> cons = new ArrayList<>();
        cons.addAll(field.getConnectors());
        cons.add(firstConnector);

        field.setConnectors(cons);

        return field;
    }

}
