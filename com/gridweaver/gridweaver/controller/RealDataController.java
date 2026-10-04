package com.gridweaver.gridweaver.controller;

import com.gridweaver.gridweaver.model.RealDataPoint;
import com.gridweaver.gridweaver.model.RealGridData;
import com.gridweaver.gridweaver.service.NppDataService;
import com.gridweaver.gridweaver.service.NppRealGridService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/real-data")
public class RealDataController {

    private final NppDataService nppDataService;
    private final NppRealGridService nppRealGridService;

    public RealDataController(
            NppDataService nppDataService,
            NppRealGridService nppRealGridService) {

        this.nppDataService = nppDataService;
        this.nppRealGridService = nppRealGridService;
    }

    @GetMapping("/demand")
    public List<RealDataPoint> getDemandData() {
        return nppDataService.getDemandData();
    }

    @GetMapping("/generation")
    public List<RealDataPoint> getGenerationData() {
        return nppDataService.getGenerationData();
    }

    @GetMapping("/current")
    public RealGridData getCurrentGridData() {
        return nppRealGridService.getCurrentGridData();
    }
}