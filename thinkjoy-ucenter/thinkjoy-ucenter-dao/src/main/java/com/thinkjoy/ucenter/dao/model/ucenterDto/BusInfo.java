package com.thinkjoy.ucenter.dao.model.ucenterDto;

import java.io.Serializable;

public class BusInfo implements Serializable {

    private String busId;
    private String name;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}