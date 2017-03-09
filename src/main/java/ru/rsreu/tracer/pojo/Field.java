package ru.rsreu.tracer.pojo;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public Field() {

    }

    public Field(List<Connector> connectors, List<Link> links) {
        this.connectors = connectors;
        this.links = links;
    }

    private List<Connector> connectors = new ArrayList<Connector>();
    private List<Trace> traces = new ArrayList<Trace>();
    private List<Link> links = new ArrayList<Link>();

    /**
     * Calculate rating of field. Rating calculation based on total traces lengths.
     * @return Field rating
     */
    public int getRating() {
        return this.traces.stream()
                .map((trace) -> trace.getLength())
                .reduce((rating, length) -> rating + length).orElse(Integer.MAX_VALUE);
    }

    /**
     * Create trace and update path channels.
     * @param link Traced link
     * @param isTopPath Flag indicated which channel used to trace link
     * @return Created trace
     */
    public Trace traceLink(Link link, boolean isTopPath) {
        List<Channel> path = new ArrayList<Channel>();
        List<Connector> pathConnectors = getConnectorsBetween(link.getFirstPin().getContainer(),
                link.getSecondPin().getContainer());
        for (Connector connector : pathConnectors) {
            Channel channel = isTopPath ? connector.getTopChannel() : connector.getBottomChannel();
            channel.setOccupancy(channel.getOccupancy() + 1);
            path.add(channel);
        }
        Trace trace = new Trace(path, link);
        this.getTraces().add(trace);
        return trace;
    }

    /**
     * Retrace trace
     * @param trace Trace which must be retraced
     * @param isTopPath New trace path orientation
     * @return Retraced trace
     */
    public Trace retrace(Trace trace, boolean isTopPath) {
        Link link = trace.getLink();
        for (Channel channel : trace.getPath()) {
            channel.setOccupancy(channel.getOccupancy() - 1);
        }
        this.getTraces().remove(this.getTraces().indexOf(trace));
        return this.traceLink(link, isTopPath);
    }

    /**
     * Find connectors placed between passed connectors
     * @param firstConnector Left bound connector
     * @param secondConnector Right bound connector
     * @return Connectors placed between passed connectors
     */
    public List<Connector> getConnectorsBetween(Connector firstConnector, Connector secondConnector) {
        List<Connector> result = new ArrayList<Connector>();

        for (Connector connector : connectors) {
            if ((connector.getX() > firstConnector.getX()) && (connector.getX() < secondConnector.getX())) {
                result.add(connector);
            }
        }
        return result;
    }

    /**
     * Check field if it acceptable solution, i.e. all field channels is not overloaded.
     * @return True if field is acceptable solution.
     */
    public boolean isAcceptableField() {
        for (Connector connector : connectors) {
            if (connector.getTopChannel().isOverloaded() || connector.getBottomChannel().isOverloaded())
                return false;
        }
        return true;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(ArrayList<Connector> connectors) {
        this.connectors = connectors;
    }

    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (connectors != null ? !connectors.equals(field.connectors) : field.connectors != null) return false;
        if (traces != null ? !traces.equals(field.traces) : field.traces != null) return false;
        return !(links != null ? !links.equals(field.links) : field.links != null);

    }

    @Override
    public int hashCode() {
        int result = connectors != null ? connectors.hashCode() : 0;
        result = 31 * result + (traces != null ? traces.hashCode() : 0);
        result = 31 * result + (links != null ? links.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Field{" +
                "connectors=" + connectors +
                ", traces=" + traces +
                ", links=" + links +
                '}';
    }
}
