package com.gridweaver.gridweaver.engine;

import com.gridweaver.gridweaver.model.Device;
import com.gridweaver.gridweaver.model.DeviceType;
import com.gridweaver.gridweaver.model.GridState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StateEngine {

    private final Map<String, Device> devices =
            new ConcurrentHashMap<>();

    public void updateDevice(Device device) {
        devices.put(device.getId(), device);
    }

    public GridState calculateGridState() {

        double totalGeneration = 0;
        double totalLoad = 0;
        double batteryCharge = 0;

        for (Device device : devices.values()) {

            if (device.getType() == DeviceType.SOLAR) {
                totalGeneration += device.getPower();
            }

            else if (device.getType() == DeviceType.LOAD) {
                totalLoad += device.getPower();
            }

            else if (device.getType() == DeviceType.BATTERY) {
                batteryCharge += device.getBatteryPercentage();
            }
        }

        double difference = totalGeneration - totalLoad;

        double gridImport = 0;
        double gridExport = 0;

        if (difference < 0) {
            gridImport = Math.abs(difference);
        } else {
            gridExport = difference;
        }

        return new GridState(
                totalGeneration,
                totalLoad,
                batteryCharge,
                gridImport,
                gridExport
        );
    }

    public int getDeviceCount() {
        return devices.size();
    }
}