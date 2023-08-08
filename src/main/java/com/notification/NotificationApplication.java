package com.notification;

import com.notification.dto.EventDto;
import com.notification.model.Event;
import com.notification.service.NotificationService;
import com.notification.service.factory.NotificationServiceFactory;
import com.notification.service.impl.NotificationServiceImpl;
import org.springframework.boot.SpringApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@SpringBootApplication
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
        List<EventDto> eventDtoList = new ArrayList<>();
        EventDto eventDto = new EventDto("", Event.EventType.Critical.name(), LocalDateTime.of(2019, 01, 07, 14, 52, 59));
        EventDto eventDto1 = new EventDto("", Event.EventType.Critical.name(), LocalDateTime.of(2019, 01, 07, 14, 53, 20));
        EventDto eventDto2 = new EventDto("", Event.EventType.Critical.name(), LocalDateTime.of(2019, 01, 07, 14, 53, 55));
        EventDto eventDto3 = new EventDto("", Event.EventType.Critical.name(), LocalDateTime.of(2019, 01, 07, 14, 54, 36));
        EventDto eventDto4 = new EventDto("", Event.EventType.Critical.name(), LocalDateTime.of(2019, 01, 07, 14, 56, 37));
        EventDto eventDto5 = new EventDto("", Event.EventType.Critical.name(), LocalDateTime.of(2019, 01, 07, 14, 54, 39));
        EventDto eventDto6 = new EventDto("", Event.EventType.Info.name(), LocalDateTime.of(2019, 01, 07, 14, 54, 55));
        EventDto eventDto7 = new EventDto("", Event.EventType.Warning.name(), LocalDateTime.of(2019, 01, 07, 14, 56, 52));
        eventDtoList.add(eventDto);
        eventDtoList.add(eventDto1);
        eventDtoList.add(eventDto2);
        eventDtoList.add(eventDto3);
        eventDtoList.add(eventDto4);
        eventDtoList.add(eventDto5);
        eventDtoList.add(eventDto6);
        eventDtoList.add(eventDto7);
        NotificationService notificationService = NotificationServiceFactory.createNotificationService(); //Factory design pattern
        eventDtoList.forEach((notificationService::triggerEvent));
    }

}
