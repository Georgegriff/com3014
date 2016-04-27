/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatchmaking.helpers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sam Waters
 */
public class GoogleGeoCodeTest {
    
    public GoogleGeoCodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void postCodeTest() {
        double[] latLng = GoogleGeoCode.getLatLngFromPostCode("GU28FD");
        System.out.print("Lat: " + latLng[0] + " Lng: " + latLng[1]);
    }
}
