package com.fsbtech.interviews;

import com.fsbtech.interviews.entities.Category;
import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.entities.MarketRefType;
import com.fsbtech.interviews.entities.SubCategory;
import com.fsbtech.interviews.repository.CategoryRepository;
import com.fsbtech.interviews.repository.EventRepository;
import com.fsbtech.interviews.repository.MarketRefTypeRepository;
import com.fsbtech.interviews.repository.SubCategoryRepository;
import com.fsbtech.interviews.service.ClientService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FsbInterviews.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MarketRefTypeRepository marketRefTypeRepository;

    @Autowired
    private JdbcTemplate jdbc;

    private Event event;

    MarketRefType marketRefType;

    private List<MarketRefType> marketRefTypes= new ArrayList<>();

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void createObjects(){

        Category category = new Category(1, "Tennis");
        categoryRepository.save(category);
        SubCategory subCategory = new SubCategory(1, "French Open", category);
        subCategoryRepository.save(subCategory);
        marketRefType = new MarketRefType(1, "Home");
        marketRefTypes.add(marketRefType);
        event = new Event(1, "Tennis Event", subCategory, null, Boolean.FALSE);
        for (MarketRefType marketRefType : marketRefTypes){
            event.addMarketReferenceType(marketRefType);
        }
        eventRepository.save(event);
    }

    @AfterEach
    public void cleanObjects(){
        marketRefType = null;
        marketRefTypes = new ArrayList<>();
        event = null;
    }


    @Test
    @Order(1)
    public void createEventService(){
        clientService.addEvent(new Event(2, "Second Event", null, marketRefTypes, Boolean.FALSE));
        Optional<Event> event = eventRepository.findById(2);
        assertTrue(event.isPresent(), "Event is present");
        assertEquals("Second Event", event.get().getName());
    }

    @Test
    @Order(2)
    public void eventCompletedTest(){
        clientService.eventCompleted(1);
        Optional<Event> event = eventRepository.findById(1);
        assertTrue(event.isPresent(), "");
        assertTrue(event.get().getCompleted());
    }

    @Test
    @Order(3)
    @Transactional
    public void attachMarketRefTypeToEventTest(){
        marketRefType = new MarketRefType(2, "Example2");
        clientService.attachMarketRefTypeToEvent(1, marketRefType );
        Optional<MarketRefType> marketRefType =  marketRefTypeRepository.findById(2);
        assertTrue(marketRefType.isPresent());
        assertEquals("Tennis Event", marketRefType.get().getEvent().getName());
    }

    @Test
    @Order(4)
    public void removeMarketRefTypeToEventTest(){
        marketRefType = new MarketRefType(3, "Example3");
        clientService.attachMarketRefTypeToEvent(1, marketRefType );
        Optional<MarketRefType> marketRefType =  marketRefTypeRepository.findById(3);
        assertTrue(marketRefType.isPresent());
        clientService.removeMarketRefTypeFromEvent(1, marketRefType.get());
        Optional<MarketRefType> marketRefType2 =  marketRefTypeRepository.findById(3);
        assertFalse(marketRefType2.isPresent());
    }

    @Test
    @Order(5)
    @Transactional
    public void futureEventNamesCollectionTestForCategory(){
        List<String> names = (List<String>) clientService.futureEventNamesCollection("Tennis","","");
        assertEquals("Tennis Event", names.get(0));
    }

    @Test
    @Order(6)
    public void futureEventNamesCollectionTestForSubCategory(){
        List<String> names = (List<String>) clientService.futureEventNamesCollection("","French Open","Home");
        assertEquals("Tennis Event", names.get(0));
    }

    @Test
    @Order(7)
    public void futureEventNamesCollectionTestForMarketRefType(){
        List<String> names = (List<String>) clientService.futureEventNamesCollection("","","Home");
        assertEquals("Tennis Event", names.get(0));
    }

    @Test
    @Order(8)
    public void futureEventNamesCollectionTestForAllNull(){
        List<String> names = (List<String>) clientService.futureEventNamesCollection("","","");
        assertTrue(names.isEmpty());
    }

    @Test
    @Order(9)
    public void dumpFullStructureTest() throws JSONException {
        String str = clientService.dumpFullStructure();
        assertNotNull(str);
    }

}
