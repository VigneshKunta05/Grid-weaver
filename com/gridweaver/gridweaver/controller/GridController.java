package com.gridweaver.gridweaver.controller;

import com.gridweaver.gridweaver.model.GridState;
import com.gridweaver.gridweaver.service.GridService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grid")
public class GridController {

    private final GridService gridService;

    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    @GetMapping("/state")
    public GridState getGridState() {
        return gridService.getGridState();
    }

    @GetMapping("/history")
    public List<GridState> getGridHistory() {
        return gridService.getGridHistory();
    }

    @PostMapping("/update")
    public GridState updateGrid(
            @RequestParam double totalGeneration,
            @RequestParam double totalLoad,
            @RequestParam double batteryCharge) {

        return gridService.updateGrid(
                totalGeneration,
                totalLoad,
                batteryCharge
        );
    }

    @PostMapping("/reset")
    public GridState resetGrid() {
        return gridService.resetGrid();
    }
}