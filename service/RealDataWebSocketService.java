package com.gridweaver.gridweaver.service;

import com.gridweaver.gridweaver.model.RealGridData;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RealDataWebSocketService {

    private final NppRealGridService nppRealGridService;
    private final SimpMessagingTemplate messagingTemplate;

    public RealDataWebSocketService(
            NppRealGridService nppRealGridService,
            SimpMessagingTemplate messagingTemplate) {

        this.nppRealGridService = nppRealGridService;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 60000)
    public void broadcastRealGridData() {

        try {

            RealGridData gridData =
                    nppRealGridService.getCurrentGridData();

            messagingTemplate.convertAndSend(
                    "/topic/real-grid-data",
                    gridData
            );

            System.out.println(
                    "Real grid data broadcasted -> Demand: "
                            + gridData.getCurrentDemand()
                            + " MW, Generation: "
                            + gridData.getTotalGeneration()
                            + " MW"
            );

        } catch (Exception e) {

            System.err.println(
                    "Failed to broadcast real grid data: "
                            + e.getMessage()
            );
        }
    }
}