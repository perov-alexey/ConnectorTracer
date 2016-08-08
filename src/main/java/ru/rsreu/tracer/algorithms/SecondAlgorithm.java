package ru.rsreu.tracer.algorithms;

import ru.rsreu.tracer.CalculateHelper;
import ru.rsreu.tracer.IOUtils;
import ru.rsreu.tracer.helpers.ChannelHelper;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SecondAlgorithm implements Algorithm {

    Field field;
    Integer counter;
    StringBuilder bldr = new StringBuilder();

    @Override
    public void execute(Field field) {
        this.field = field;
        IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");

        System.out.println("-------------------------------");
        counter = 0;


        List<Trace> traces = new ArrayList<Trace>();
        List<Link> links = field.getLinks();

        Collections.sort(links, new Comparator<Link>() {
            @Override
            public int compare(Link firstLink, Link secondLink) {
                double firstLinkLength = CalculateHelper.getLinkLength(firstLink);
                double secondLinkLength = CalculateHelper.getLinkLength(secondLink);
                if (firstLinkLength < secondLinkLength) {
                    return -1;
                } else if (firstLinkLength > secondLinkLength) {
                    return 1;
                }
                return 0;
            }
        });

        for (Link link : links) {
            List<Connector> betweenConnectors = FieldHelper.getConnectorsBetween(field, link.getFirstPin().getContainer(), link.getSecondPin().getContainer());
            if (!isPathOverloaded(betweenConnectors, true)) {
                traces.add(traceLink(betweenConnectors, link, true));
                field.setTraces(traces);
                IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
            } else if (!isPathOverloaded(betweenConnectors, false)) {
                traces.add(traceLink(betweenConnectors, link, false));
                field.setTraces(traces);
                IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
            } else {
                try {
                    retracePreviousLink(traces, betweenConnectors);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    field.setTraces(new ArrayList<Trace>());
                    return;
                }
            }
        }
        field.setTraces(traces);

        IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
    }

    private boolean isPathOverloaded(List<Connector> connectors, boolean isTopPath) {
        for (Connector connector : connectors) {
            Channel channel = isTopPath ? connector.getTopChannel() : connector.getBottomChannel();
            if (ChannelHelper.isChannelFull(channel)) {
                return true;
                //TODO Break for?
            }
        }
        return false;
    }

    private Trace traceLink(List<Connector> connectors, Link link, boolean isTopPath) {
        counter++;
        System.out.println(counter);
        List<Channel> path = new ArrayList<Channel>();
        for (Connector connector : connectors) {
            Channel channel = isTopPath ? connector.getTopChannel() : connector.getBottomChannel();
            channel.setOccupancy(channel.getOccupancy() + 1);
            path.add(channel);
        }
        return new Trace(path, link);
    }

    private void retracePreviousLink(List<Trace> traces, List<Connector> connectors) throws Exception {
        if (traces.size() > 0) {
            Trace lastTrace = traces.remove(traces.size() - 1);
            field.setTraces(traces);
            IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
            if (lastTrace.getPath().get(0).isTop()) {
                traces.add(traceLink(connectors, lastTrace.getLink(), false));
                field.setTraces(traces);
                IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
            } else {
                retracePreviousLink(traces, connectors);
                if (!isPathOverloaded(connectors, true)) {
                    traces.add(traceLink(connectors, lastTrace.getLink(), true));
                    field.setTraces(traces);
                    IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
                } else if (!isPathOverloaded(connectors, false)) {
                    traces.add(traceLink(connectors, lastTrace.getLink(), false));
                    field.setTraces(traces);
                    IOUtils.writeString(IOUtils.getString(field) + ",\n", "result.json");
                } else {
                    try {
                        retracePreviousLink(traces, connectors);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else {
            throw new Exception("Traces array is empty");
        }
    }
}
