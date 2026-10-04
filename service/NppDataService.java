package com.gridweaver.gridweaver.service;

import com.gridweaver.gridweaver.model.RealDataPoint;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class NppDataService {

    private final RestClient restClient;

    private static final String BASE_URL =
            "https://npp.gov.in";

    private static final ZoneId INDIA_ZONE =
            ZoneId.of("Asia/Kolkata");

    public NppDataService() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public List<RealDataPoint> getDemandData() {

        return getLatestAvailableData(
                "/dashBoard/demandmet1chartdata?date={date}"
        );
    }

    public List<RealDataPoint> getGenerationData() {

        return getLatestAvailableData(
                "/dashBoard/demandmet2chartdata?date={date}"
        );
    }

    private List<RealDataPoint> getLatestAvailableData(String endpoint) {

        LocalDate date = LocalDate.now(INDIA_ZONE);

        // Try today first, then previous 7 days if today's data is unavailable
        for (int i = 0; i <= 7; i++) {

            LocalDate dateToTry = date.minusDays(i);

            try {

                List<RealDataPoint> data = restClient.get()
                        .uri(endpoint, dateToTry.toString())
                        .retrieve()
                        .body(
                                new ParameterizedTypeReference<List<RealDataPoint>>() {}
                        );

                if (data != null && !data.isEmpty()) {
                    System.out.println(
                            "NPP data found for date: " + dateToTry
                    );

                    return data;
                }

                System.out.println(
                        "No NPP data for date: " + dateToTry
                );

            } catch (Exception e) {

                System.err.println(
                        "Failed to fetch NPP data for "
                                + dateToTry
                                + ": "
                                + e.getMessage()
                );
            }
        }

        return List.of();
    }
}