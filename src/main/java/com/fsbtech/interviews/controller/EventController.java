package com.fsbtech.interviews.controller;

import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.entities.MarketRefType;
import com.fsbtech.interviews.repository.EventRepository;
import com.fsbtech.interviews.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    ClientService clientService;

    @PostMapping(value = "/")
    public Event createEvent(@RequestBody Event event) {
        clientService.addEvent(event);
        Optional<Event> event1 =  clientService.getEvent(event.getId());
        return event1.isPresent() == true ? event1.get():null;
    }

    @PutMapping(value = "/{id}")
    public Event updateEvent(@PathVariable int id) {
        clientService.eventCompleted(id);
        Optional<Event> event =  clientService.getEvent(id);
        return event.isPresent() == true ? event.get():null;
    }

    @PutMapping(value = "/addMarketRef/{id}")
    public Event attachMarketRefTypeToEvent(@PathVariable int id, @RequestBody MarketRefType marketRefType){
        clientService.attachMarketRefTypeToEvent(id, marketRefType);
        Optional<Event> event =  clientService.getEvent(id);
        return event.isPresent() == true ? event.get():null;
    }

    @DeleteMapping(value = "/removeMarketRef/{id}")
    public Event removeMarketRefTypeFromEvent(@PathVariable int id, @RequestBody MarketRefType marketRefType){
        clientService.removeMarketRefTypeFromEvent(id, marketRefType);
        Optional<Event> event =  clientService.getEvent(id);
        return event.isPresent() == true ? event.get():null;
    }

    @GetMapping(value = "/search")
    public List<String> futureEventNamesCollection(@RequestParam(required = false) String category,
                                                   @RequestParam(required = false) String subCategory,
                                                   @RequestParam(required = false) String marketRefType){
       return (List<String>) clientService.futureEventNamesCollection(category, subCategory, marketRefType);
    }

    @GetMapping(value = "/")
    public String dumpFullStructure(){
        return clientService.dumpFullStructure();
    }

}
