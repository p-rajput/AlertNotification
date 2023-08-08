package com.notification.service.factory;

import com.notification.service.NotificationService;
import com.notification.service.impl.NotificationServiceImpl;

//Factory design pattern
public class NotificationServiceFactory {
    public static NotificationService createNotificationService() {
        return new NotificationServiceImpl();
    }
}
