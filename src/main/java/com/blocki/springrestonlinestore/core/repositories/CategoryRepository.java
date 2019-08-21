package com.blocki.springrestonlinestore.core.repositories;

import com.blocki.springrestonlinestore.core.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);
}
