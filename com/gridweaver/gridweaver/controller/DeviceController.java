package com.gridweaver.gridweaver.controller;

import com.gridweaver.gridweaver.model.Device;
import com.gridweaver.gridweaver.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable String id) {
        return deviceService.getDeviceById(id);
    }

    @PostMapping
    public Device addDevice(@RequestBody Device device) {
        return deviceService.addDevice(device);
    }

    @PutMapping("/{id}")
    public Device updateDevice(
            @PathVariable String id,
            @RequestBody Device device) {

        return deviceService.updateDevice(id, device);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDevice(@PathVariable String id) {
        return deviceService.deleteDevice(id);
    }

    @DeleteMapping
    public void deleteAllDevices() {
        deviceService.deleteAllDevices();
    }
}