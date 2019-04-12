package com.example.rpp_sem4_lab2;

import org.json.JSONArray;

public class DataHold {

    private static volatile DataHold instance;
    private JSONArray data;

    public static DataHold getInstance() {
        DataHold Intance = instance;

        if (Intance == null) {
            instance = Intance = new DataHold();
        }
        return Intance;
    }

    public JSONArray getData(){
        return this.data;
    }
    public void setData(JSONArray data){
        this.data = data;
    }
}
