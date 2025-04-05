package com.masai.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleEngineService {
    private final DeviceService deviceService;

    public void processTemperatureRule(double temperature, String deviceId) {
        log.debug("Processing temperature rule for device {}", deviceId);

        if (temperature > 40) {
            deviceService.createAlarm(deviceId, "CRITICAL_TEMPERATURE",
                    "Temperature exceeds critical threshold: " + temperature);
            log.warn("Critical temperature alarm triggered: {}", temperature);
        } else if (temperature > 35) {
            deviceService.createAlarm(deviceId, "WARNING_TEMPERATURE",
                    "Temperature exceeds warning threshold: " + temperature);
            log.warn("Warning temperature alarm triggered: {}", temperature);
        }
    }

    public void processPowerRule(double power, String deviceId) {
        log.debug("Processing power rule for device {}", deviceId);

        if (power > 4000) {
            deviceService.createAlarm(deviceId, "CRITICAL_POWER",
                    "Power exceeds critical threshold: " + power);
            log.warn("Critical power alarm triggered: {}", power);
        } else if (power > 3000) {
            deviceService.createAlarm(deviceId, "WARNING_POWER",
                    "Power exceeds warning threshold: " + power);
            log.warn("Warning power alarm triggered: {}", power);
        }
    }
}