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

        //TODO Need unit test for this method

        List<Channel> path = new ArrayList<Channel>();
        for (Connector connector : connectors) {
            Channel channel = isTopPath ? connector.getTopChannel() : connector.getBottomChannel();
            channel.setOccupancy(channel.getOccupancy() + 1);
            path.add(channel);
        }
        Trace trace = new Trace(path, link);
        this.getTraces().add(trace);
        return trace;
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
