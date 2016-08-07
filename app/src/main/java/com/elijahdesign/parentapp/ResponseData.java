package com.elijahdesign.parentapp;

/**
 * Created by elijah on 8/5/2016.
 */
public class ResponseData {

    String userID;
    String child_longitude;
    String child_latitude;

    ResponseData(String userID, String child_longitude, String child_latitude){

        this.userID = userID;
        this.child_longitude = child_longitude;
        this.child_latitude = child_latitude;

    }
}
