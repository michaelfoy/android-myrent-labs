package org.wit.myrent.models;

import java.util.Date;
import java.util.Random;

/**
 * @file Residence.java
 * @brief Model for a residence object including relevant methods
 * @version 2016.09.27
 * @author michaelfoy
 */
public class Residence
{
    public Long id;
    public Long date;

    //a latitude longitude pair
    //example "52.4566,-6.5444"
    private String geolocation;
    public boolean rented;

    public Residence()
    {
        id = new Random().nextLong();
        date = new Date().getTime();
    }

    public void setGeolocation(String geolocation)
    {
        this.geolocation = geolocation;
    }

    public String getGeolocation()
    {
        return geolocation;
    }

    public String getDateString() {
        return "Registered:" + dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }

}