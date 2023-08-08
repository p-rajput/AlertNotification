package com.notification.service.impl;

import com.notification.dao.UserEventDao;
import com.notification.dto.CacheDto;
import com.notification.dto.EventDto;
import com.notification.dto.ThreshHoldValueDto;
import com.notification.model.Event;
import com.notification.model.User;
import com.notification.service.CacheService;
import com.notification.service.NotificationService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private UserEventDao userEventDao;
    private Map<String, ThreshHoldValueDto> threshHoldValue;

    public NotificationServiceImpl() {
        userEventDao = new UserEventDao();
        ThreshHoldValueDto info = new ThreshHoldValueDto(2, 10, 40);
        ThreshHoldValueDto warn = new ThreshHoldValueDto(2, 20, 30);
        ThreshHoldValueDto critical = new ThreshHoldValueDto(2, 100, 10);
        threshHoldValue = new HashMap<>();
        threshHoldValue.put(Event.EventType.Info.name(), info);
        threshHoldValue.put(Event.EventType.Warning.name(), warn);
        threshHoldValue.put(Event.EventType.Critical.name(), critical);
    }

    @Override // can be a rabbitMq consumer and we can increase the concurrency of this consumer according to load
    public void triggerEvent(EventDto eventDto) {
        String eventName = eventDto.getEventType();
        CacheDto cacheValue = CacheService.getInstance().getKey(eventName);
        if (!Objects.isNull(cacheValue)) {
            long interval = cacheValue.getFirstTimeEventOccurred().until(eventDto.getLocalDateTime(), ChronoUnit.SECONDS);
            ThreshHoldValueDto threshHoldValueDto = threshHoldValue.get(eventName);
            //Means if 10 critical events occurs in 100 sec or less then it then notify user and wait for 100 sec
            if (threshHoldValueDto.getMaxCount().compareTo(cacheValue.getFrequency()) >= 0 && threshHoldValueDto.getMaxTime().compareTo((int) interval) >= 0) {
                List<User> usersList = userEventDao.getUserForEvent(eventName);
                usersList.forEach((user -> {
                    System.out.println("Sending Event: " + eventName + " to user: " + user.getName());
                }));
                cacheValue.setFirstTimeEventOccurred(eventDto.getLocalDateTime());
                cacheValue.setFrequency(1);
                cacheValue.setLastTimeMaxCountOccurred(LocalDateTime.now());
            } else {
                if (threshHoldValueDto.getMaxTime().compareTo((int) interval) <= 0) {// if event doest not exceeded its max count in Max Time interval then start as fresh
                    CacheDto cacheDto = new CacheDto(eventDto.getLocalDateTime(), 1, null);
                    CacheService.getInstance().putInCache(eventName, cacheDto);
                } else {
                    if (!Objects.isNull(cacheValue.getLastTimeMaxCountOccurred())) {// if event reaches its max count in before or equal to max time interval
                        long internalBetweenNowAndLastTimeMaxCountOccurred = cacheValue.getLastTimeMaxCountOccurred().until(LocalDateTime.now(), ChronoUnit.SECONDS);
                        if (internalBetweenNowAndLastTimeMaxCountOccurred >= threshHoldValueDto.getWaitTime()) {//Means start counting of that type event after wait time is over.
                            cacheValue.setFrequency(cacheValue.getFrequency() + 1);
                        } else {
                            cacheValue.setFrequency(0);
                        }
                    } else {
                        //it event event doest not exceeded its max count and still fall in Max Time interval  then keep counting frequency
                        cacheValue.setFrequency(cacheValue.getFrequency() + 1);
                    }
                }
            }
            CacheService.getInstance().putInCache(eventName, cacheValue);
        } else {
            CacheDto cacheDto = new CacheDto(eventDto.getLocalDateTime(), 1, null);
            CacheService.getInstance().putInCache(eventName, cacheDto);
        }
    }
}
