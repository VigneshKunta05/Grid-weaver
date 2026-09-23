package com.gridweaver.gridweaver.service;

import com.gridweaver.gridweaver.model.DeviceState;
import org.springframework.stereotype.Service;


@Service
public class DeviceStateService {

    public DeviceState determineState(
            double gridLoad,
            double batteryCharge,
            boolean fault
    ) {

        // Fault has the highest priority
        if (fault) {
            return DeviceState.FAULT;
        }

        // High grid load -> battery discharges
        if (gridLoad > 80) {
            return DeviceState.DISCHARGE;
        }

        // Low battery -> idle
        if (batteryCharge <= 10) {
            return DeviceState.IDLE;
        }

        // Otherwise battery can charge
        return DeviceState.CHARGE;
    }
}