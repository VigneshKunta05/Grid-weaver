package com.gridweaver.gridweaver.service;

import com.gridweaver.gridweaver.engine.GridEngine;
import com.gridweaver.gridweaver.model.Device;
import com.gridweaver.gridweaver.model.DeviceType;
import com.gridweaver.gridweaver.model.GridState;
import com.gridweaver.gridweaver.repository.DeviceRepository;
import com.gridweaver.gridweaver.repository.GridStateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridService {

    private final GridEngine gridEngine;
    private final GridStateRepository gridStateRepository;
    private final DeviceRepository deviceRepository;

    public GridService(
            GridStateRepository gridStateRepository,
            DeviceRepository deviceRepository) {

        this.gridEngine = new GridEngine();
        this.gridStateRepository = gridStateRepository;
        this.deviceRepository = deviceRepository;
    }

    public GridState getGridState() {

        return gridStateRepository.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElseGet(() ->
                        gridEngine.calculateGridState(
                                0.0,
                                0.0,
                                0.0
                        )
                );
    }

    public GridState updateGrid(
            double totalGeneration,
            double totalLoad,
            double batteryCharge) {

        GridState newState = gridEngine.calculateGridState(
                totalGeneration,
                totalLoad,
                batteryCharge
        );

        return gridStateRepository.save(newState);
    }

    public GridState updateGridFromDevices() {

        double totalGeneration = 0.0;
        double totalLoad = 0.0;
        double totalBattery = 0.0;

        List<Device> devices = deviceRepository.findAll();

        for (Device device : devices) {

            if (!device.isActive()) {
                continue;
            }

            if (device.getType() == DeviceType.SOLAR) {

                totalGeneration += device.getPower();

            } else if (device.getType() == DeviceType.LOAD) {

                totalLoad += device.getPower();

            } else if (device.getType() == DeviceType.BATTERY) {

                totalBattery += device.getBatteryPercentage();
            }
        }

        GridState newState = gridEngine.calculateGridState(
                totalGeneration,
                totalLoad,
                totalBattery
        );

        return gridStateRepository.save(newState);
    }

    public List<GridState> getGridHistory() {
        return gridStateRepository.findAll();
    }

    public GridState resetGrid() {

        gridStateRepository.deleteAll();

        GridState resetState = gridEngine.calculateGridState(
                0.0,
                0.0,
                0.0
        );

        return gridStateRepository.save(resetState);
    }
}