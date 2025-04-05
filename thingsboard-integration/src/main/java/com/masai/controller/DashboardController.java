package com.masai.controller;


import com.masai.model.Alarm;
import com.masai.model.Device;
import com.masai.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DeviceService deviceService;

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getDevices());
    }

    @GetMapping("/devices/{deviceId}/alarms")
    public ResponseEntity<List<Alarm>> getDeviceAlarms(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceAlarms(deviceId));
    }

    @GetMapping("/devices/status")
    public ResponseEntity<Map<String, String>> getDevicesStatus() {
        List<Device> devices = deviceService.getDevices();
        Map<String, String> statusMap = new HashMap<>();

        devices.forEach(device -> {
            List<Alarm> alarms = deviceService.getDeviceAlarms(device.getId());
            String status = alarms.isEmpty() ? "OK" :
                    alarms.stream().anyMatch(a -> "CRITICAL".equals(a.getSeverity())) ? "CRITICAL" : "WARNING";
            statusMap.put(device.getName(), status);
        });

        return ResponseEntity.ok(statusMap);
    }
}