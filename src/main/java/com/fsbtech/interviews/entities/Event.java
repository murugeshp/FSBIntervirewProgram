package com.fsbtech.interviews.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private SubCategory subCategory;

    @JsonManagedReference
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<MarketRefType> marketRefTypes;

    @Column
    private Boolean completed;

    public Event(){super();}

    public Event(Integer id, String name, SubCategory subCategory, Collection<MarketRefType> marketRefTypes, Boolean completed)
    {
        this.id = id;
        this.name = name;
        this.subCategory = subCategory;
        this.marketRefTypes = marketRefTypes;
        this.completed = completed;
    }


    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public SubCategory getSubCategory()
    {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public void setMarketRefTypes(Collection<MarketRefType> marketRefTypes) {
        this.marketRefTypes = marketRefTypes;
    }

    public Boolean getCompleted() 
    { 
	return completed; 
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Collection<MarketRefType> getMarketRefTypes() {
        return marketRefTypes;
    }

    public void addMarketReferenceType(MarketRefType marketRefType) {
        if (marketRefTypes == null) {
            marketRefTypes = new ArrayList<>();
        }
        marketRefType.setEvent(this);
        marketRefTypes.add(marketRefType);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subCategory=" + subCategory +
                ", marketRefTypes=" + marketRefTypes +
                ", completed=" + completed +
                '}';
    }
}
