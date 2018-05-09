package com.hdk.adsltrial.server;

/**
 * Created by monamagdy on 5/9/18.
 */

public class TimeLineModel {
    private String message;

    public enum STATUS {
        ACTIVE,
        INACTIVE
    };

    STATUS status;

    public TimeLineModel(String msg, STATUS status){
     this.message = msg;
     this.status = status;
    }

    public String getMessage(){
        return this.message;
    }


    public void setStatus(STATUS status) {
        this.status= status;
    }

    public STATUS getStatus() {
        return status;
    }
}
