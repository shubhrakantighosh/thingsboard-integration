package com.masai.controller;


import com.masai.model.TelemetryRequest;
import com.masai.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/telemetry")
    public ResponseEntity<Void> sendTelemetry(@RequestBody TelemetryRequest request) {
        deviceService.sendTelemetry(request.getDeviceToken(), request.getTelemetry());
        return ResponseEntity.ok().build();
    }
}