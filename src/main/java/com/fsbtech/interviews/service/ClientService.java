package com.fsbtech.interviews.service;

import com.fsbtech.interviews.Client;
import com.fsbtech.interviews.entities.Category;
import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.entities.MarketRefType;
import com.fsbtech.interviews.entities.SubCategory;
import com.fsbtech.interviews.repository.CategoryRepository;
import com.fsbtech.interviews.repository.EventRepository;
import com.fsbtech.interviews.repository.MarketRefTypeRepository;
import com.fsbtech.interviews.repository.SubCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService implements Client {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public void eventCompleted(Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        event.get().setCompleted(Boolean.TRUE);
        eventRepository.save(event.get());
    }

    public void attachMarketRefTypeToEvent(Integer id, MarketRefType marketRefType) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            event.get().addMarketReferenceType(marketRefType);
            eventRepository.save(event.get());
        }
    }

    public void removeMarketRefTypeFromEvent(Integer id, MarketRefType marketRefType) {
        Optional<Event> event = eventRepository.findById(id);
        marketRefType.setEvent(event.get());
        if(event.isPresent()){
            event.get().getMarketRefTypes().removeIf(m -> marketRefType.getMarketRefId().equals(m.getMarketRefId()) && marketRefType.getMarketRefName().equals(m.getMarketRefName()));
            eventRepository.save(event.get());
        }
    }

    public Collection<String> futureEventNamesCollection(String cat, String subcat, String marketRefName) {
        List<String> names = (List<String>) eventRepository.findAllActiveEvents(cat,subcat,marketRefName);
        return names;
    }

    public String dumpFullStructure() {
        return eventRepository.findAll().toString();
    }
}
