
package com.mishkun.weatherapp.data.google_places.detailCityInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailCityInfo {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
