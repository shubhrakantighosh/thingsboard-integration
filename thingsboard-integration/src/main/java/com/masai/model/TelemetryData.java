package com.masai.model;

import lombok.Data;
import java.util.Map;

@Data
public class TelemetryData {
    private String deviceId;
    private Map<String, Object> values;
    private long timestamp;
}