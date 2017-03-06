package ru.rsreu.tracer.pojo;

public class Link {

    public Link() {}

    public Link(Pin firstPin, Pin secondPin) {
        this.firstPin = firstPin;
        this.secondPin = secondPin;
    }

    private Pin firstPin;
    private Pin secondPin;

    /**
     * Calculate link length
     * @param isTopPath Link path
     * @return Link's length if they were traced by top or bottom (depends on isTopPath flag)
     */
    public int getLength(boolean isTopPath) {
        Pin firstPin = this.getFirstPin();
        Pin secondPin = this.getSecondPin();

        // If first pin placed righter, then switch firstPin and secondPin
        if (firstPin.getContainer().getX() > secondPin.getContainer().getX()) {
            Pin tempPin = firstPin;
            firstPin = secondPin;
            secondPin = tempPin;
        }

        int horizontalLength = secondPin.getContainer().getX() + secondPin.getX() -
                firstPin.getContainer().getX() - firstPin.getX();
        int verticalLength;
        if (isTopPath) {
            verticalLength = firstPin.getY() + secondPin.getY();
        } else {
            verticalLength = firstPin.getContainer().getHeight() - firstPin.getY() +
                    secondPin.getContainer().getHeight() - secondPin.getY();
        }

        return horizontalLength + verticalLength;
    }

    public Pin getFirstPin() {
        return firstPin;
    }

    public void setFirstPin(Pin firstPin) {
        this.firstPin = firstPin;
    }

    public Pin getSecondPin() {
        return secondPin;
    }

    public void setSecondPin(Pin secondPin) {
        this.secondPin = secondPin;
    }


    //TODO Try to minimize it.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (firstPin != null) {
            if (!firstPin.equals(link.firstPin)) {
                if (!firstPin.equals(link.secondPin) || !secondPin.equals(link.firstPin)) {
                    return false;
                }
            } else if (link.firstPin != null){
                return false;
            }
        }

        if (secondPin != null) {
            if (!secondPin.equals(link.secondPin)) {
                if (!secondPin.equals(link.firstPin) || !firstPin.equals(link.secondPin)) {
                    return false;
                }
            } else if (link.secondPin != null){
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstPin != null ? firstPin.hashCode() : 0;
        result = 31 * result + (secondPin != null ? secondPin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "firstPin=" + firstPin +
                ", secondPin=" + secondPin +
                '}';
    }
}
