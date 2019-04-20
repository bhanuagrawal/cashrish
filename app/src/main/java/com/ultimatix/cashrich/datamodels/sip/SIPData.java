
package com.ultimatix.cashrich.datamodels.sip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIPData {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("sensex")
    @Expose
    private String sensex;
    @SerializedName("equity")
    @Expose
    private String equity;
    @SerializedName("point")
    @Expose
    private String point;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSensex() {
        return sensex;
    }

    public void setSensex(String sensex) {
        this.sensex = sensex;
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

}
