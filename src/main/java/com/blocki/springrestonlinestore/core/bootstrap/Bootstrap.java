package com.blocki.springrestonlinestore.core.bootstrap;

import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.debug("Filling categories");

        Category clothes = Category.builder().id(1L).name("Clothes").build();
        Category food = Category.builder().id(2L).name("Food").build();

       // categoryRepository.save(clothes);
        //categoryRepository.save(food);
    }
}
