package com.fsbtech.interviews.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="subcategory")
public class SubCategory implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String ref;

    @OneToOne
    private Category category;

    public SubCategory(){}

    public SubCategory(Integer id, String ref, Category category)
    {
        this.id = id;
        this.ref = ref;
        this.category = category;
    }

    public Integer getId()
    {
        return id;
    }

    public String getRef()
    {
        return ref;
    }

    public Category getCategory()
    {
        return category;
    }

}
