package com.com3014.group1.projectmatching.model;

import java.io.Serializable;

/**
 * @author Sam Waters
 *
 * A location defined by latitude and longitude
 */
public class Location implements Serializable {

    private double latitude;
    private double longitude;
    private String stringLocation;

    public Location() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

        Double latDistance = Math.toRadians(latitude - location.getLatitude());
        Double lonDistance = Math.toRadians(longitude - location.getLongitude());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location.getLatitude())) * Math.cos(Math.toRadians(latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

}
