package com.gridweaver.gridweaver.service;

import com.gridweaver.gridweaver.model.RealDataPoint;
import com.gridweaver.gridweaver.model.RealGridData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NppRealGridService {

    private final NppDataService nppDataService;

    public NppRealGridService(NppDataService nppDataService) {
        this.nppDataService = nppDataService;
    }

    public RealGridData getCurrentGridData() {

        List<RealDataPoint> demandData =
                nppDataService.getDemandData();

        List<RealDataPoint> generationData =
                nppDataService.getGenerationData();

        RealGridData result = new RealGridData();

        // Latest demand value
        if (!demandData.isEmpty()) {

            RealDataPoint latestDemand =
                    demandData.get(demandData.size() - 1);

            result.setCurrentDemand(
                    latestDemand.getValue_of_data()
            );

            result.setUpdatedOn(
                    latestDemand.getUpdated_on()
            );
        }

        // Find latest generation timestamp
        long latestGenerationTimestamp = 0;

        for (RealDataPoint point : generationData) {

            if (point.getUpdated_on() > latestGenerationTimestamp) {
                latestGenerationTimestamp = point.getUpdated_on();
            }
        }

        // Read generation values from latest timestamp
        double totalGeneration = 0;

        for (RealDataPoint point : generationData) {

            if (point.getUpdated_on() != latestGenerationTimestamp) {
                continue;
            }

            String type = point.getName_of_data();
            double value = point.getValue_of_data();

            totalGeneration += value;

            switch (type) {

                case "THERMAL GENERATION":
                    result.setThermalGeneration(value);
                    break;

                case "HYDRO GENERATION":
                    result.setHydroGeneration(value);
                    break;

                case "SOLAR GENERATION":
                    result.setSolarGeneration(value);
                    break;

                case "WIND GENERATION":
                    result.setWindGeneration(value);
                    break;

                case "GAS GENERATION":
                    result.setGasGeneration(value);
                    break;

                case "NUCLEAR GENERATION":
                    result.setNuclearGeneration(value);
                    break;

                default:
                    break;
            }
        }

        result.setTotalGeneration(totalGeneration);

        // Calculate grid import/export
        double demand = result.getCurrentDemand();

        if (demand > totalGeneration) {

            result.setGridImport(demand - totalGeneration);
            result.setGridExport(0);

        } else if (totalGeneration > demand) {

            result.setGridExport(totalGeneration - demand);
            result.setGridImport(0);

        } else {

            result.setGridImport(0);
            result.setGridExport(0);
        }

        // Use latest timestamp
        if (latestGenerationTimestamp > result.getUpdatedOn()) {
            result.setUpdatedOn(latestGenerationTimestamp);
        }

        return result;
    }
}