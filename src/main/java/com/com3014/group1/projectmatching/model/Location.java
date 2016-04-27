/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.model;

/**
 * @author Sam Waters
 *
 * Note: This can be expanded to allow for other types of location, not just lat
 * and lon
 */
public class Location {

    private double lat;
    private double lon;
    private String stringLocation;

    public Location() {
    }

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getStringLocation() {
        return stringLocation;
    }

    public void setStringLocation(String stringLocation) {
        this.stringLocation = stringLocation;
    }
    

    /**
     * Get the distance between this location and another in metres
     *
     * Note: I got this from SO, don't think it would need to be referenced but
     * just incase:
     * http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
     *
     * @param location The other location
     * @return The distance between this location and the given position
     */
    public double getDistance(Location location) {
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat - location.getLat());
        Double lonDistance = Math.toRadians(lon - location.getLon());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location.getLat())) * Math.cos(Math.toRadians(lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

}
