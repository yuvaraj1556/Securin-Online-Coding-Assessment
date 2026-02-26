package com.example.recipe.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.recipe.dto.PageResponseDTO;
import com.example.recipe.dto.PostResponseDTO;
import com.example.recipe.service.RecipeService;

@RestController
public class RecipeController {
	
	private final RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadRecipes(@RequestParam("file") MultipartFile file) {
		try {
			recipeService.saveRecipes(file);
			return ResponseEntity.ok("Recipes uploaded successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload recipes: " + e.getMessage());
		}
	}
	
	@PostMapping("/recipes")
	public ResponseEntity<PostResponseDTO> addRecipe(@RequestBody PostResponseDTO recipe){
		try {
			PostResponseDTO savedRecipe = recipeService.addRecipe(recipe);
			return ResponseEntity.ok(savedRecipe);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/recipes/top")
	public ResponseEntity<PageResponseDTO> getTopRecipes(@RequestParam(defaultValue = "5",required=false) int limit){
		try {
			PageResponseDTO topRecipes = recipeService.getTopRecipes(limit);
			return ResponseEntity.ok(topRecipes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
