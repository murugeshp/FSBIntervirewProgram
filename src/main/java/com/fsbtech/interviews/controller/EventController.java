package com.fsbtech.interviews.controller;

import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    ClientService clientService;

    @PostMapping(value = "/")
    public void createEvent(@RequestBody Event event) {
        clientService.addEvent(event);
    }

/*    @PutMapping(value = "/{id}")
    public Event updateEvent(@PathVariable int id) {
        clientService.eventCompleted(id);
        Optional<Event> event =  clientService.getEvent(id);
        return event.isPresent() == true ? event.get():null;
    }*/


}
