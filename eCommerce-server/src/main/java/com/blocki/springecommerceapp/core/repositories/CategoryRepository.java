package com.blocki.springecommerceapp.core.repositories;

import com.blocki.springecommerceapp.core.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);
}
