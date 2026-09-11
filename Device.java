package com.gridweaver.gridweaver.model;

public class Device {

    private String id;
    private DeviceType type;
    private double power;
    private double batteryPercentage;

    public Device() {
    }

    public Device(String id, DeviceType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public DeviceType getType() {
        return type;
    }

    public double getPower() {
        return power;
    }

    public double getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setBatteryPercentage(double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }
}