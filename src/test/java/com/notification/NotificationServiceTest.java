package com.notification;

import com.notification.dao.UserEventDao;
import com.notification.dto.CacheDto;
import com.notification.dto.EventDto;
import com.notification.model.Event;
import com.notification.model.User;
import com.notification.service.CacheService;
import com.notification.service.NotificationService;
import com.notification.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


class NotificationServiceTest {

    private CacheService cacheService;
    private UserEventDao userEventDao;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        cacheService = mock(CacheService.class);
        userEventDao = mock(UserEventDao.class);
        notificationService = new NotificationServiceImpl(); // Use the original constructor
    }

    @Test
    void testTriggerEvent_NotifyUsers() {
        // Mock data
        EventDto eventDto = new EventDto("EventType1", Event.EventType.Warning.name(), LocalDateTime.now());
        CacheDto cacheDto = new CacheDto(LocalDateTime.now(), 10, LocalDateTime.now().minusSeconds(60));

        // Mock cache service behavior
        when(cacheService.getKey(eventDto.getEventType())).thenReturn(cacheDto);
        doNothing().when(cacheService).putInCache(anyString(), any(CacheDto.class));

        // Mock UserEventDao behavior
        List<User> usersList = new ArrayList<>();
        User user = new User(1, "User1");
        usersList.add(user);
        when(userEventDao.getUserForEvent(eventDto.getEventType())).thenReturn(usersList);

        // Test
        notificationService.triggerEvent(eventDto);
    }

    @Test
    void testTriggerEvent_NoNotificationNeeded() {
        // Mock data
        EventDto eventDto = new EventDto("EventType2", Event.EventType.Warning.name(), LocalDateTime.now());
        CacheDto cacheDto = new CacheDto(LocalDateTime.now(), 5, LocalDateTime.now().minusSeconds(30));

        // Mock cache service behavior
        when(cacheService.getKey(eventDto.getEventType())).thenReturn(cacheDto);
        doNothing().when(cacheService).putInCache(anyString(), any(CacheDto.class));

        // Test
        notificationService.triggerEvent(eventDto);
    }

    @Test
    void testTriggerEvent_CacheValueIsNull() {
        // Mock data
        EventDto eventDto = new EventDto("EventType3", Event.EventType.Warning.name(), LocalDateTime.now());

        // Mock cache service behavior
        when(cacheService.getKey(eventDto.getEventType())).thenReturn(null);
        doNothing().when(cacheService).putInCache(anyString(), any(CacheDto.class));

        // Test
        notificationService.triggerEvent(eventDto);
    }
}
