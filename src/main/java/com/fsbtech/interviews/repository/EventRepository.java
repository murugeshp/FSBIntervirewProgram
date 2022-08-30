package com.fsbtech.interviews.repository;

import com.fsbtech.interviews.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "SELECT DISTINCT eve.NAME FROM Event eve "
            + " LEFT JOIN MARKETREFTYPE m ON eve.ID =  m.FK_EVENT_ID"
            + " LEFT JOIN SUBCATEGORY sc  ON eve.SUB_CATEGORY_ID = sc.ID"
            + " LEFT JOIN CATEGORY c ON sc.CATEGORY_ID = c.ID"
            + " WHERE eve.COMPLETED= 'FALSE' AND"
            + " m.MARKET_REF_NAME = ?3"
            + " OR c.REF= ?1"
            + " OR  sc.REF= ?2", nativeQuery = true)
    Collection<String> findAllActiveEvents(String cat, String SubCat, String matRef);

    public List<Event> findAll();
}
