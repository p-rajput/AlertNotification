package com.notification.dao;

import com.notification.model.Event;
import com.notification.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserEventDao {
    public Map<String, List<User>> userEventMap;

    public UserEventDao() {
        userEventMap = new HashMap<>();
        User user = new User(1, "User1");
        User user1 = new User(2, "User2");
        User user2 = new User(3, "User3");
        User user3 = new User(4, "User4");
        List<User> infoEventList = new ArrayList<>();
        List<User> criticalEventList = new ArrayList<>();
        List<User> warningEventList = new ArrayList<>();
        infoEventList.add(user);
        infoEventList.add(user1);
        criticalEventList.add(user2);
        criticalEventList.add(user3);
        criticalEventList.add(user);
        warningEventList.add(user1);
        warningEventList.add(user3);
        userEventMap.put(Event.EventType.Info.name(), infoEventList);
        userEventMap.put(Event.EventType.Critical.name(), criticalEventList);
        userEventMap.put(Event.EventType.Warning.name(), warningEventList);
    }


    public List<User> getUserForEvent(String event) {
        return userEventMap.get(event);
    }
}
