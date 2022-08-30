package com.fsbtech.interviews.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="marketreftype")
public class MarketRefType implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer marketRefId;

    @Column
    private String marketRefName;

    public Event getEvent() {
        return event;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_event_id")
    private Event event;

    public MarketRefType(){}

    public MarketRefType(Integer marketRefId, String marketRefName)
    {
        this.marketRefId = marketRefId;
        this.marketRefName = marketRefName;
    }

    public Integer getMarketRefId() 
    {
        return marketRefId;
    }

    public String getMarketRefName() 
    {
        return marketRefName;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setMarketRefId(Integer marketRefId) {
        this.marketRefId = marketRefId;
    }

}
