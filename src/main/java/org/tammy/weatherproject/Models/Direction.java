package org.tammy.weatherproject.Models;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;


public class Direction {

    @NotNull
    private String origin;

    @NotNull
    private String destination;

    // Default constructor
    public Direction() {
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
