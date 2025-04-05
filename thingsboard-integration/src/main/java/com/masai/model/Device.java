package com.masai.model;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Device {
    private String id;
    private String name;
    private String type;
    private String label;
    private LocalDateTime createdTime;
}