package com.example.recipe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.recipe.dto.NutrientsDTO;
import com.example.recipe.dto.PageResponseDTO;
import com.example.recipe.dto.PostResponseDTO;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final int BATCH_SIZE = 1000;
	
	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	public void saveRecipes(MultipartFile file) throws Exception{
		
		Map<String,RecipeDTO> recipes = objectMapper.readValue(file.getInputStream(), new TypeReference<Map<String, RecipeDTO>>(){});
		List<Recipe> batch = new ArrayList<>();
		for(RecipeDTO dto : recipes.values()) {
			Recipe recipe = new Recipe();
			recipe.setId(dto.getId());
			recipe.setTitle(dto.getTitle());
			recipe.setCuisine(dto.getCuisine());
			recipe.setRating(dto.getRating());
			recipe.setPrep_time(dto.getPrep_time());
			recipe.setCook_time(dto.getCook_time());
			recipe.setTotal_time(dto.getTotal_time());
			recipe.setDescription(dto.getDescription());
			recipe.setNutrients(objectMapper.writeValueAsString(dto.getNutrients()));
			recipe.setServes(dto.getServes());
			batch.add(recipe);
			if(batch.size() >= BATCH_SIZE) {
				recipeRepository.saveAll(batch);
				batch.clear();
			}
		}
		if(!batch.isEmpty()) {
			recipeRepository.saveAll(batch);
		}
	}
	
	public PostResponseDTO addRecipe(PostResponseDTO recipe) {
		Recipe r = new Recipe();
		r.setTitle(recipe.getTitle());
		r.setCuisine(recipe.getCuisine());
		r.setRating(recipe.getRating());
		r.setPrep_time(recipe.getPrep_time());
		r.setCook_time(recipe.getCook_time());
		r.setDescription(recipe.getDescription());
		r.setTotal_time(recipe.getPrep_time() + recipe.getCook_time());
		recipe.setTotal_time(recipe.getPrep_time() + recipe.getCook_time());
		NutrientsDTO nutrients = recipe.getNutrients();
		try {
			r.setNutrients(objectMapper.writeValueAsString(nutrients));
		} catch (Exception e) {
			r.setNutrients("{}");
		}
		r.setServes(recipe.getServes());
		recipeRepository.save(r);
		return recipe;
	}

	public PageResponseDTO getTopRecipes(int limit) {
		PageResponseDTO response = new PageResponseDTO();
		response.setData(recipeRepository.findByRatingSortedAndLimit(limit));
		return response;
	}
	

}
