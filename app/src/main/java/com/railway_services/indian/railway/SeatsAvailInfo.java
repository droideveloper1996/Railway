package com.railway_services.indian.railway;

public class SeatsAvailInfo {

    public String mDate;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmAvailablility() {
        return mAvailablility;
    }

    public void setmAvailablility(String mAvailablility) {
        this.mAvailablility = mAvailablility;
    }

    public SeatsAvailInfo(String mDate, String mAvailablility) {
        this.mDate = mDate;
        this.mAvailablility = mAvailablility;
    }

    public String mAvailablility;
}
