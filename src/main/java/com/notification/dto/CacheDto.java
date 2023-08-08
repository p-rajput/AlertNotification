package com.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheDto {
    private LocalDateTime firstTimeEventOccurred;
    private Integer frequency;
    private LocalDateTime lastTimeMaxCountOccurred;
}
