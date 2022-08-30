package com.fsbtech.interviews.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category")
public class Category implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String ref;

    public Category(){}

    public Category(Integer id, String ref)
    {
        this.id = id;
        this.ref = ref;
    }

    public Integer getId()
    {
        return id;
    }

    public String getRef()
    {
        return ref;
    }


}
