package ru.rsreu.tracer.utils;

import ru.rsreu.tracer.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Random field generator
 */
public class FieldGenerator {

    private static final int FIRST_CONNECTOR_PADDING = 10;

    private static final int MIN_CONNECTOR_PADDING = 30;
    private static final int MAX_CONNECTOR_PADDING = 50;

    private static final int MIN_CONNECTOR_Y = 100;
    private static final int MAX_CONNECTOR_Y = 100;

    private static final int MIN_CONNECTOR_HEIGHT = 200;
    private static final int MAX_CONNECTOR_HEIGHT = 200;

    //Now all connectors will be same width
    private static final int MIN_CONNECTOR_WIDTH = 100;
    private static final int MAX_CONNECTOR_WIDTH = 100;

    /**
     * Generate random field
     * @param connectorsAmount Amount of connectors in generated field
     * @param pinsAmount Amount of pins in each connector
     * @param columnPinsAmount Amount of pin's columns in each connector
     * @param linksAmount Amount of links in generated field
     * @param minChannelCapacity Minimum capacity of the channels
     * @param maxChannelCapacity Maximum capacity of the channels
     * @return Generated field
     */
    public Field generateField(int connectorsAmount, int pinsAmount, int columnPinsAmount, int linksAmount,
                               int minChannelCapacity, int maxChannelCapacity) {
        List<Connector> connectors = new ArrayList<>();
        List<Link> links = new ArrayList<>();

        for (int i = 0; i < connectorsAmount; i++) {
            if (connectors.isEmpty()) {
                connectors.add(generateConnector(null, pinsAmount, columnPinsAmount, minChannelCapacity,
                        maxChannelCapacity));
            } else {
                connectors.add(generateConnector(connectors.get(connectors.size() - 1), pinsAmount, columnPinsAmount,
                        minChannelCapacity, maxChannelCapacity));
            }
        }

        for (int i = 0; i < linksAmount; i++) {
            links.add(generateLink(connectors));
        }

        return new Field(connectors, links);
    }

    /**
     * Generate connector connector
     * @param lastConnector Previous connector to calculate new x/y coordinates
     * @param pinsAmount Amount of pins in each column
     * @param columnPinsAmount Amount of columns with pins in the generated connector
     * @param minChannelCapacity Minimum capacity of the channels
     * @param maxChannelCapacity Maximum capacity of the channels
     * @return Generated connector
     */
    public Connector generateConnector(Connector lastConnector, int pinsAmount, int columnPinsAmount,
                                       int minChannelCapacity, int maxChannelCapacity) {
        if (pinsAmount <= 0 || columnPinsAmount <= 0) {
            throw new IllegalArgumentException("Amount of pins in the connector must be more than 0");
        }

        int connectorPadding = FIRST_CONNECTOR_PADDING;

        if (lastConnector != null) {
            connectorPadding = lastConnector.getX() + lastConnector.getWidth() 
                    + new Random().nextInt(MAX_CONNECTOR_PADDING) + MIN_CONNECTOR_PADDING;
        }

        Connector connector = new Connector(connectorPadding, 
                ThreadLocalRandom.current().nextInt(MIN_CONNECTOR_Y, MAX_CONNECTOR_Y + 1), 
                ThreadLocalRandom.current().nextInt(MIN_CONNECTOR_HEIGHT, MAX_CONNECTOR_HEIGHT + 1),
                ThreadLocalRandom.current().nextInt(MIN_CONNECTOR_WIDTH, MAX_CONNECTOR_WIDTH + 1));

        List<Pin> pins = new ArrayList<>();
        for (int i = 1; i < columnPinsAmount + 1; i++) {
            for (int j = 1; j < pinsAmount + 1; j++) {
                pins.add(generatePin(connector, i, columnPinsAmount, j, pinsAmount));
            }
        }

        connector.setPins(pins);
        connector.setTopChannel(generateChannel(connector, true, minChannelCapacity, maxChannelCapacity));
        connector.setBottomChannel(generateChannel(connector, false, minChannelCapacity, maxChannelCapacity));

        return connector;
    }

    /**
     * Generate pin
     * @param connector Container for generated pin
     * @param columnNumber Column number to calculate pin horizontal position
     * @param amountOfColumns Amount of columns to calculate pin horizontal position
     * @param rowNumber  Row number to calculate pin vertical position
     * @param amountOfRows Amount of rows to calculate pin vertical position
     * @return Generated pin
     */
    public Pin generatePin(Connector connector, int columnNumber, int amountOfColumns, int rowNumber, int amountOfRows) {
        return new Pin(connector.getWidth() * columnNumber / (amountOfColumns + 1),
                connector.getHeight() * rowNumber / (amountOfRows + 1), connector);
    }

    /**
     * Generate channel
     * @param connector For this connector generates channel
     * @param isTop Set to true is you want create top channel
     * @param minChannelCapacity Minimum capacity of the channels
     * @param maxChannelCapacity Maximum capacity of the channels
     * @return Generated channel
     */
    public Channel generateChannel(Connector connector, boolean isTop, int minChannelCapacity, int maxChannelCapacity) {
        return new Channel(connector, ThreadLocalRandom.current().nextInt(minChannelCapacity, maxChannelCapacity), isTop);
    }

    /**
     * Generate link
     * @param connectors All connectors to choose random pins to wire
     * @return Generated link
     */
    public Link generateLink(List<Connector> connectors) {
        int firstConnectorNumber = ThreadLocalRandom.current().nextInt(0, connectors.size());
        int secondConnectorNumber = ThreadLocalRandom.current().nextInt(0, connectors.size());
        while (Math.abs(firstConnectorNumber - secondConnectorNumber) < 2) {
            firstConnectorNumber = ThreadLocalRandom.current().nextInt(0, connectors.size());;
            secondConnectorNumber = ThreadLocalRandom.current().nextInt(0, connectors.size());;
        }

        Connector firstConnector = connectors.get(firstConnectorNumber);
        Connector secondConnector = connectors.get(secondConnectorNumber);

        return new Link(firstConnector.getPins().get(ThreadLocalRandom.current().nextInt(0, firstConnector.getPins().size())),
                secondConnector.getPins().get(ThreadLocalRandom.current().nextInt(0, secondConnector.getPins().size())));
    }
}
