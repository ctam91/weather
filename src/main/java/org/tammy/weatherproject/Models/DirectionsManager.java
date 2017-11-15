package org.tammy.weatherproject.Models;

import com.google.maps.GeoApiContext;

public class DirectionsManager {

    private final GeoApiContext geoApiContext;

    public DirectionsManager(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }
}
