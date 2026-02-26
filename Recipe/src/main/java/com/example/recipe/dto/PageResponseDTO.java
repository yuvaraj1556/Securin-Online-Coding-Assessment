package com.example.recipe.dto;

import java.util.List;

import com.example.recipe.model.Recipe;


public class PageResponseDTO {

	
	private List<Recipe> data;
	
	public PageResponseDTO() {
	}
	
	public PageResponseDTO(List<Recipe> data) {
		this.data = data;
	}

	public List<Recipe> getData() {
		return data;
	}

	public void setData(List<Recipe> data) {
		this.data = data;
	}

	

}
