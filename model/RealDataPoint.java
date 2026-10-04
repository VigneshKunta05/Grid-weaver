package com.gridweaver.gridweaver.model;

public class RealDataPoint {

    private long updated_on;
    private String name_of_data;
    private double value_of_data;

    public RealDataPoint() {
    }

    public long getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(long updated_on) {
        this.updated_on = updated_on;
    }

    public String getName_of_data() {
        return name_of_data;
    }

    public void setName_of_data(String name_of_data) {
        this.name_of_data = name_of_data;
    }

    public double getValue_of_data() {
        return value_of_data;
    }

    public void setValue_of_data(double value_of_data) {
        this.value_of_data = value_of_data;
    }
}