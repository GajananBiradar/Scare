package com.scare.repository;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.scare.model.Category;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepoTests {

	@Autowired
	private CategoryRepo categoryRepo;

    // JUnit test for save category operation
    //@DisplayName("JUnit test for save category operation")
    @Test
    public void givenCategoryObject_whenSave_thenReturnSavedCategory(){
    
    	 //given - precondition or setup
    	Category category = Category.builder()
    			.category_id("CA6778")
    			.category_name("Home Appliances")
    			.categoryStatus("publish")
    			.created_on(new Date())
    			.created_by("gaj")
    			.build();
    	
    	// when - action or the behaviour that we are going test
    	Category savedCategory = categoryRepo.save(category);   	

        // then - verify the output
    	Assertions.assertThat(savedCategory).isNotNull();
    	Assertions.assertThat(savedCategory.getCategory_id()).isEqualTo("CA6778");
    	Assertions.assertThat(savedCategory.getCategory_name()).isEqualTo("Home Appliances");
    	Assertions.assertThat(savedCategory.getCategoryStatus()).isEqualTo("publish");   			
    }
	
	
}
