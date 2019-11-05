package com.example.loutre.POJO;

/**
 * The response contains a pagination field (not used in this project)
 * and an array of data (used)
 */

public class InstagramResponse {

    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }
}
