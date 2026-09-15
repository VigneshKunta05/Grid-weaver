package com.gridweaver.gridweaver.repository;

import com.gridweaver.gridweaver.model.Device;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DeviceRepository {

    private final List<Device> devices = new ArrayList<>();

    public List<Device> findAll() {
        return devices;
    }

    public Device findById(String id) {
        return devices.stream()
                .filter(device -> device.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Device save(Device device) {
        Device existingDevice = findById(device.getId());

        if (existingDevice != null) {
            devices.remove(existingDevice);
        }

        devices.add(device);
        return device;
    }

    public boolean deleteById(String id) {
        Device device = findById(id);

        if (device != null) {
            devices.remove(device);
            return true;
        }

        return false;
    }

    public void deleteAll() {
        devices.clear();
    }
}