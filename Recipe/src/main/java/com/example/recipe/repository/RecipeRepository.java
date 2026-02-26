package com.example.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.recipe.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	@Query(value = "SELECT * FROM recipes ORDER BY rating DESC LIMIT :limit", nativeQuery = true)
	List<Recipe> findByRatingSortedAndLimit(@Param("limit") int limit);
}
