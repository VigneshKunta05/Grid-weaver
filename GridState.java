package com.gridweaver.gridweaver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GridState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalGeneration;
    private double totalLoad;
    private double batteryCharge;
    private double gridImport;
    private double gridExport;

    public GridState() {
    }

    public GridState(
            double totalGeneration,
            double totalLoad,
            double batteryCharge,
            double gridImport,
            double gridExport) {

        this.totalGeneration = totalGeneration;
        this.totalLoad = totalLoad;
        this.batteryCharge = batteryCharge;
        this.gridImport = gridImport;
        this.gridExport = gridExport;
    }

    public Long getId() {
        return id;
    }

    public double getTotalGeneration() {
        return totalGeneration;
    }

    public double getTotalLoad() {
        return totalLoad;
    }

    public double getBatteryCharge() {
        return batteryCharge;
    }

    public double getGridImport() {
        return gridImport;
    }

    public double getGridExport() {
        return gridExport;
    }
}