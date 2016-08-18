package ru.rsreu.tracer.pojo;

import java.util.ArrayList;
import java.util.List;

public class Connector {

    private int x;
    private int y;
    private int height;
    private int width;
    private List<Pin> pins = new ArrayList<Pin>();
    private Channel topChannel;
    private Channel bottomChannel;

    public Connector() {

    }

    public Connector(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Connector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public Connector setPins(List<Pin> pins) {
        this.pins = pins;
        return this;
    }

    public Channel getTopChannel() {
        return topChannel;
    }

    public Channel getBottomChannel() {
        return bottomChannel;
    }

    public void setTopChannel(Channel topChannel) {
        this.topChannel = topChannel;
    }

    public void setBottomChannel(Channel bottomChannel) {
        this.bottomChannel = bottomChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connector connector = (Connector) o;

        if (x != connector.x) return false;
        if (y != connector.y) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Connector{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                ", width=" + width +
                ", pins=" + pins +
                '}';
    }
}
