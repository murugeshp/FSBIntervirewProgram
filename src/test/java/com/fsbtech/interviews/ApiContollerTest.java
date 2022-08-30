package com.fsbtech.interviews;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsbtech.interviews.entities.Category;
import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.entities.MarketRefType;
import com.fsbtech.interviews.entities.SubCategory;
import com.fsbtech.interviews.repository.CategoryRepository;
import com.fsbtech.interviews.repository.EventRepository;
import com.fsbtech.interviews.repository.MarketRefTypeRepository;
import com.fsbtech.interviews.repository.SubCategoryRepository;
import com.fsbtech.interviews.service.ClientService;
import org.assertj.core.error.ShouldExist;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = FsbInterviews.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiContollerTest {

    @Autowired
    ClientService clientService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

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

    @Autowired
    ObjectMapper objectMapper;

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
    public void createEventApiTest() throws Exception {
        clientService.addEvent(new Event(2, "Second Event", null, marketRefTypes, Boolean.FALSE));

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                        .andExpect(status().isOk());

        Optional<Event> event = eventRepository.findById(2);
        assertTrue(event.isPresent(), "Event is present");
        assertEquals("Second Event", event.get().getName());

    }

    @Test
    @Order(2)
    public void eventCompletedTestApiTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        Optional<Event> event = eventRepository.findById(1);
        assertTrue(event.isPresent(), "Event is updated");
        assertTrue(event.get().getCompleted());
    }

    @Test
    @Order(3)
    @Transactional
    public void attachMarketRefTypeToEventApiTest() throws Exception {
        marketRefType = new MarketRefType(2, "Example2");

        mockMvc.perform(MockMvcRequestBuilders.put("/addMarketRef/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marketRefType)))
                        .andExpect(status().isOk());

        Optional<MarketRefType> marketRefType =  marketRefTypeRepository.findById(2);
        assertTrue(marketRefType.isPresent());
        assertEquals("Tennis Event", marketRefType.get().getEvent().getName());
    }

    @Test
    @Order(4)
    public void removeMarketRefTypeToEventApiTest() throws Exception {
        marketRefType = new MarketRefType(3, "Example3");
        clientService.attachMarketRefTypeToEvent(1, marketRefType );
        Optional<MarketRefType> marketRefType =  marketRefTypeRepository.findById(3);
        assertTrue(marketRefType.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/removeMarketRef/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marketRefType)))
                        .andExpect(status().isOk());

        Optional<MarketRefType> marketRefType2 =  marketRefTypeRepository.findById(3);
        assertFalse(marketRefType2.isPresent());
    }

    @Test
    @Order(5)
    public void futureEventNamesCollectionTestForCategory() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("category","Tennis"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Order(6)
    public void futureEventNamesCollectionTestForSubCategory() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("subCategory","French Open"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Order(7)
    public void futureEventNamesCollectionTestForMarketRefType() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("marketRefType","Home"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Order(8)
    public void futureEventNamesCollectionTestForAllNull() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(9)
    public void dumpFullStructureTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
