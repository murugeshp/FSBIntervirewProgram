package com.fsbtech.interviews.repository;

import com.fsbtech.interviews.entities.Category;
import com.fsbtech.interviews.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
