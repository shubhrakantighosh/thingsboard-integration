package com.masai.service;

import com.masai.model.Alarm;
import com.masai.model.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {
    private final WebClient webClient;

    public void sendTelemetry(String deviceToken, Map<String, Object> telemetry) {
        try {
            webClient.post()
                    .uri("/api/v1/{deviceToken}/telemetry", deviceToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(telemetry)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            log.info("Telemetry sent successfully for device {}", deviceToken);
        } catch (Exception e) {
            log.error("Failed to send telemetry for device {}", deviceToken, e);
        }
    }

    public void createAlarm(String deviceId, String alarmType, String alarmDetails) {
        try {
            Alarm alarm = new Alarm();
            alarm.setType(alarmType);
            alarm.setSeverity("CRITICAL".equals(alarmType) ? "CRITICAL" : "WARNING");
            alarm.setDetails(alarmDetails);

            webClient.post()
                    .uri("/api/alarm/{deviceId}", deviceId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(alarm)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            log.warn("Alarm created: {} for device {}", alarmType, deviceId);
        } catch (Exception e) {
            log.error("Failed to create alarm for device {}", deviceId, e);
        }
    }

    public List<Device> getDevices() {
        try {
            return webClient.get()
                    .uri("/api/tenant/devices")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Device>>() {})
                    .block();
        } catch (Exception e) {
            log.error("Failed to fetch devices", e);
            return Collections.emptyList();
        }
    }

    public List<Alarm> getDeviceAlarms(String deviceId) {
        try {
            return webClient.get()
                    .uri("/api/alarm/{deviceId}", deviceId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Alarm>>() {})
                    .block();
        } catch (Exception e) {
            log.error("Failed to fetch alarms for device {}", deviceId, e);
            return Collections.emptyList();
        }
    }
}