package com.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    public enum EventType {
        Warning,Critical,Info
    }
    private int id;
    private EventType type;
}
