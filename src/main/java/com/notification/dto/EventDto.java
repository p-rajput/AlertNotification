package com.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventDto {
    private String data;
    private String eventType;
    private LocalDateTime localDateTime;
}
