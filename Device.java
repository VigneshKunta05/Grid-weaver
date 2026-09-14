package com.gridweaver.gridweaver.model;

public class Device {

    private String id;
    private String name;
    private DeviceType type;
    private double power;
    private double batteryPercentage;
    private boolean active;

    public Device() {
        this.active = true;
    }

    public Device(String id, DeviceType type) {
        this.id = id;
        this.type = type;
        this.active = true;
    }

    public Device(String id, String name, DeviceType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}