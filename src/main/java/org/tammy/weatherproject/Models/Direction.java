package org.tammy.weatherproject.Models;

import org.springframework.boot.autoconfigure.domain.EntityScan;


public class Direction {

    private String origin;
    private String destination;

    public Direction(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
