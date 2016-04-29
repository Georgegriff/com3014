/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatchmaking.helpers;

/**
 *
 * @author Sam Waters
 */
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GoogleGeoCode {

    public static double[] getLatLngFromPostCode(String postCode) throws NumberFormatException {
        return getLocation(postCode);
    }

    private static double[] getLocation(String code) {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDXA9RWELOLKJQ7Bh8pZpF-DS-Ul1Ug7Z8");
        GeocodingResult[] results = null;
        try {
            results = GeocodingApi.geocode(context, code).await();
        } catch (Exception ex) {
            Logger.getLogger(GoogleGeoCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(results != null){
            return new double[]{results[0].geometry.location.lat, results[0].geometry.location.lng};
        }
        return new double[]{Double.valueOf(0), Double.valueOf(0)};
    }   
}
