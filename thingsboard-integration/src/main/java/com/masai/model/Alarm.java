package com.masai.model;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Alarm {
    private String id;
    private String type;
    private String severity;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String details;
    private String originator;
}