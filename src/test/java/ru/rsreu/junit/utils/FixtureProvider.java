package ru.rsreu.junit.utils;

import ru.rsreu.tracer.pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provide fixtures for test
 */
public class FixtureProvider {

    /**
     * Create field which contain overloaded top channel. This field used to test branch and bound algorithm
     * implementation on channel overload
     * @return Field fixture
     */
    public static Field getTopChannelOverloadedField() {
        // First connector
        Pin firstConnectorFirstPin = new Pin(50, 20);
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

}
