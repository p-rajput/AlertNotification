package com.notification.service;

import com.notification.dto.EventDto;

public interface NotificationService {

    public void triggerEvent(EventDto eventDto);
}
