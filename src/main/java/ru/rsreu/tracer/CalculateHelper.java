package ru.rsreu.tracer;

import ru.rsreu.tracer.pojo.Link;

public class CalculateHelper {

    public static double getLinkLength(Link link) {
        double xFirstPinPosition = link.getFirstPin().getContainer().getX() + link.getFirstPin().getX();
        double xSecondPinPosition = link.getSecondPin().getContainer().getX() + link.getSecondPin().getX();
        double yFirstPinPosition = link.getFirstPin().getContainer().getY() + link.getFirstPin().getY();
        double ySecondPinPosition = link.getSecondPin().getContainer().getY() + link.getSecondPin().getY();

        double xDistance = Math.abs(xFirstPinPosition - xSecondPinPosition);
        double yDistance = Math.abs(yFirstPinPosition - ySecondPinPosition);
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

}
