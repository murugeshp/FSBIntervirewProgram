package com.fsbtech.interviews.repository;

import com.fsbtech.interviews.entities.Event;
import com.fsbtech.interviews.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
}
