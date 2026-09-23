package com.gridweaver.gridweaver.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GridWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public GridWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Broadcast overall grid state
    public void broadcastGridState(Object gridState) {

        messagingTemplate.convertAndSend(
                "/topic/grid-state",
                gridState
        );
    }

    // Broadcast individual device state
    public void broadcastDeviceState(
            String deviceId,
            String deviceType,
            String state) {

        Map<String, String> deviceState = new HashMap<>();

        deviceState.put("deviceId", deviceId);
        deviceState.put("deviceType", deviceType);
        deviceState.put("state", state);

        messagingTemplate.convertAndSend(
                "/topic/device-state",
                deviceState
        );
    }
}