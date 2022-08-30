package com.fsbtech.interviews;

import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.entities.MarketRefType;
import com.fsbtech.interviews.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    ClientService clientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Override
    public void run(String...args) throws Exception {
        //Event event1 = new Event(1,"First Event", null, List.of(new MarketRefType(1, "Example1")), true);
       // LOGGER.info("Add event is called");
        //clientService.addEvent(event1);
        //clientService.attachMarketRefTypeToEvent(1,  new MarketRefType(2, "Example2") );
        /*clientService.eventCompleted(1);
        //clientService.updateMarketRefType(1, event1);
        clientService.attachMarketRefTypeToEvent(1, null);
        clientService.removeMarketRefTypeFromEvent(1, null);*/
        //clientService.futureEventNamesCollection("","","");
        //clientService.dumpFullStructure();
    }
}
