package com.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreshHoldValueDto {
    private Integer maxCount;
    private Integer maxTime;
    private Integer waitTime;
}
