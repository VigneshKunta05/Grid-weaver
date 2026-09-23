package com.gridweaver.gridweaver.service;

import com.gridweaver.gridweaver.model.Device;
import com.gridweaver.gridweaver.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(String id) {
        return deviceRepository.findById(id);
    }

    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device updateDevice(String id, Device device) {
        Device existingDevice = deviceRepository.findById(id);

        if (existingDevice == null) {
            return null;
        }

        device.setId(id);

        return deviceRepository.save(device);
    }

    public boolean deleteDevice(String id) {
        return deviceRepository.deleteById(id);
    }

    public void deleteAllDevices() {
        deviceRepository.deleteAll();
    }
}