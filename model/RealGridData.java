package com.gridweaver.gridweaver.model;

public class RealGridData {

    private double currentDemand;
    private double totalGeneration;

    private double gridImport;
    private double gridExport;

    private double thermalGeneration;
    private double hydroGeneration;
    private double solarGeneration;
    private double windGeneration;
    private double gasGeneration;
    private double nuclearGeneration;

    private long updatedOn;

    public RealGridData() {
    }

    public double getCurrentDemand() {
        return currentDemand;
    }

    public void setCurrentDemand(double currentDemand) {
        this.currentDemand = currentDemand;
    }

    public double getTotalGeneration() {
        return totalGeneration;
    }

    public void setTotalGeneration(double totalGeneration) {
        this.totalGeneration = totalGeneration;
    }

    public double getGridImport() {
        return gridImport;
    }

    public void setGridImport(double gridImport) {
        this.gridImport = gridImport;
    }

    public double getGridExport() {
        return gridExport;
    }

    public void setGridExport(double gridExport) {
        this.gridExport = gridExport;
    }

    public double getThermalGeneration() {
        return thermalGeneration;
    }

    public void setThermalGeneration(double thermalGeneration) {
        this.thermalGeneration = thermalGeneration;
    }

    public double getHydroGeneration() {
        return hydroGeneration;
    }

    public void setHydroGeneration(double hydroGeneration) {
        this.hydroGeneration = hydroGeneration;
    }

    public double getSolarGeneration() {
        return solarGeneration;
    }

    public void setSolarGeneration(double solarGeneration) {
        this.solarGeneration = solarGeneration;
    }

    public double getWindGeneration() {
        return windGeneration;
    }

    public void setWindGeneration(double windGeneration) {
        this.windGeneration = windGeneration;
    }

    public double getGasGeneration() {
        return gasGeneration;
    }

    public void setGasGeneration(double gasGeneration) {
        this.gasGeneration = gasGeneration;
    }

    public double getNuclearGeneration() {
        return nuclearGeneration;
    }

    public void setNuclearGeneration(double nuclearGeneration) {
        this.nuclearGeneration = nuclearGeneration;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }
}