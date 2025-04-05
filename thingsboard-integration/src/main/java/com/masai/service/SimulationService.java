package com.masai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationService {
    private final DeviceService deviceService;
    private final RuleEngineService ruleEngineService;
    private final Random random = new Random();

    @Value("${simulation.interval}")
    private long simulationInterval;

    @PostConstruct
    public void init() {
        scheduleTemperatureSensor();
        schedulePowerMeter();
    }

    private void scheduleTemperatureSensor() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                double temperature = -10 + (50 - (-10)) * random.nextDouble();
                double humidity = 20 + (80 - 20) * random.nextDouble();

                Map<String, Object> telemetry = new HashMap<>();
                telemetry.put("temperature", temperature);
                telemetry.put("humidity", humidity);

                deviceService.sendTelemetry("TEMPERATURE_DEVICE", telemetry);
                log.debug("Sent temperature telemetry: {}", telemetry);

                ruleEngineService.processTemperatureRule(temperature, "TEMPERATURE_DEVICE");
            } catch (Exception e) {
                log.error("Error in temperature simulation", e);
            }
        }, 0, simulationInterval, TimeUnit.MILLISECONDS);
    }

    private void schedulePowerMeter() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                double voltage = 110 + (240 - 110) * random.nextDouble();
                double current = 0 + 20 * random.nextDouble();
                double power = voltage * current;

                Map<String, Object> telemetry = new HashMap<>();
                telemetry.put("voltage", voltage);
                telemetry.put("current", current);
                telemetry.put("power", power);

                deviceService.sendTelemetry("POWER_DEVICE", telemetry);
                log.debug("Sent power telemetry: {}", telemetry);

                ruleEngineService.processPowerRule(power, "POWER_DEVICE");
            } catch (Exception e) {
                log.error("Error in power simulation", e);
            }
        }, 0, simulationInterval, TimeUnit.MILLISECONDS);
    }
}