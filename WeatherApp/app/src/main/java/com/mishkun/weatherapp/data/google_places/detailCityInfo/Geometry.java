
package com.mishkun.weatherapp.data.google_places.detailCityInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    @Expose
    private LocationCity locationCity;

    public LocationCity getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(LocationCity locationCity) {
        this.locationCity = locationCity;
    }
}
