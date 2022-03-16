package com.example.springmvc;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    public List<Event> getEvents() {
        Event event1 = Event.builder()
                .name("spring mvc 1")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2022,3,16,10,0))
                .endDateTime(LocalDateTime.of(2022,3,16,12,0))
                .build();

        Event event2 = Event.builder()
                .name("spring mvc 2")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2022,3,23,10,0))
                .endDateTime(LocalDateTime.of(2022,3,23,12,0))
                .build();

        return List.of(event1, event2);
    }
}
