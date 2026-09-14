package com.gridweaver.gridweaver.engine;

import com.gridweaver.gridweaver.model.GridState;

public class GridEngine {

    public GridState calculateGridState(
            double totalGeneration,
            double totalLoad,
            double batteryCharge) {

        double gridImport = 0.0;
        double gridExport = 0.0;

        if (totalGeneration < totalLoad) {
            gridImport = totalLoad - totalGeneration;
        } else if (totalGeneration > totalLoad) {
            gridExport = totalGeneration - totalLoad;
        }

        return new GridState(
                totalGeneration,
                totalLoad,
                batteryCharge,
                gridImport,
                gridExport
        );
    }
}