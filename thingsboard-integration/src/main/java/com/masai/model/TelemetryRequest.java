package com.masai.model;


import lombok.Data;
import java.util.Map;

@Data
public class TelemetryRequest {
    private String deviceToken;
    private Map<String, Object> telemetry;
}