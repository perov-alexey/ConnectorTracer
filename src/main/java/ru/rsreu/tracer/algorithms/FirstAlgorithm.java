package ru.rsreu.tracer.algorithms;

import com.rits.cloning.Cloner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rsreu.tracer.CalculateHelper;
import ru.rsreu.tracer.helpers.ChannelHelper;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is strange old algorithm, I think I can develope this after all algorithms only
 */
public class FirstAlgorithm implements Algorithm {

    private Logger logger = LogManager.getLogger(FirstAlgorithm.class);

    private Field field;
    private List<Field> steps = new ArrayList<>();
    private boolean debugEnabled;

    @Override
    public List<Field> execute(Field field, boolean debugEnabled, boolean requireBestSolution) {
        this.field = field;
        this.debugEnabled = debugEnabled;
        steps.add(new Cloner().deepClone(field));

        logger.debug("Start tracing!");

        List<Trace> traces = new ArrayList<Trace>();
        List<Link> links = field.getLinks();

        Collections.sort(links, (firstLink, secondLink) -> {
            double firstLinkLength = CalculateHelper.getLinkLength(firstLink);
            double secondLinkLength = CalculateHelper.getLinkLength(secondLink);
            if (firstLinkLength > secondLinkLength) {
                return -1;
            } else if (firstLinkLength < secondLinkLength) {
                return 1;
            }
            return 0;
        });

        try {
            for (Link link : links) {
                putLinkToField(link, traces);
            }
        } catch (Exception e) {
            logger.error("Objective doesn't have any decisions");
            updateFieldTraces(field, new ArrayList<>());
            return steps;
        }

        field.setTraces(traces);
        steps.add(new Cloner().deepClone(field));
        return steps;
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
            logUndoTrace(lastTrace.getLink(), lastTrace.getPath().get(0).isTop());
            for (Channel channel : lastTrace.getPath()) {
                channel.setOccupancy(channel.getOccupancy() - 1);
            }
            if (lastTrace.getPath().get(0).isTop()) {
                if (!isPathOverloaded(connectors, false)) {
                    traces.add(traceLink(connectors, lastTrace.getLink(), false));
                    updateFieldTraces(field, traces);
                    logTrace(lastTrace.getLink(), false);
                } else {
                    try {
                        retracePreviousLink(traces, connectors);
                        putLinkToField(lastTrace.getLink(), traces);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                if (!isPathOverloaded(connectors, true)) {
                    traces.add(traceLink(connectors, lastTrace.getLink(), true));
                    updateFieldTraces(field, traces);
                    logTrace(lastTrace.getLink(), true);
                } else {
                    try {
                        retracePreviousLink(traces, connectors);
                        putLinkToField(lastTrace.getLink(), traces);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else {
            throw new Exception("Traces array is empty");
        }
    }

    private void updateFieldTraces(Field field, List<Trace> traces) {
        field.setTraces(traces);
        if (isDebugEnabled()) {
            steps.add(new Cloner().deepClone(field));
        }
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    private void putLinkToField(Link link, List<Trace> traces) throws Exception {
        List<Connector> betweenConnectors = FieldHelper.getConnectorsBetween(field, link.getFirstPin().getContainer(), link.getSecondPin().getContainer());
        if (!isPathOverloaded(betweenConnectors, true)) {
            traces.add(traceLink(betweenConnectors, link, true));
            updateFieldTraces(field, traces);
            logTrace(link, true);
        } else if (!isPathOverloaded(betweenConnectors, false)) {
            traces.add(traceLink(betweenConnectors, link, false));
            updateFieldTraces(field, traces);
            logTrace(link, false);
        } else {
            retracePreviousLink(traces, betweenConnectors);
        }
    }

    private void logTrace(Link link, boolean isTopChannel) {
        logger.debug("Trace {}-{}:{}-{}->{}-{}:{}-{} was traced to {}",
                link.getFirstPin().getContainer().getX(),
                link.getFirstPin().getContainer().getY(),
                link.getFirstPin().getX(),
                link.getFirstPin().getY(),
                link.getSecondPin().getContainer().getX(),
                link.getSecondPin().getContainer().getY(),
                link.getSecondPin().getX(),
                link.getSecondPin().getY(),
                isTopChannel ? "top" : "bottom"
        );
    }

    private void logUndoTrace(Link link, boolean isTopChannel) {
        logger.debug("Trace {}-{}:{}-{}->{}-{}:{}-{} was removed from {}",
                link.getFirstPin().getContainer().getX(),
                link.getFirstPin().getContainer().getY(),
                link.getFirstPin().getX(),
                link.getFirstPin().getY(),
                link.getSecondPin().getContainer().getX(),
                link.getSecondPin().getContainer().getY(),
                link.getSecondPin().getX(),
                link.getSecondPin().getY(),
                isTopChannel ? "top" : "bottom"
        );
    }

}
