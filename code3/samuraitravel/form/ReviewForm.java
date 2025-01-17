package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotNull;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

import lombok.Data;

@Data
public class ReviewForm {
	private Integer id;
	
	private House houseId;
	
	private User userId;
	
	@NotNull(message = "評価を選択してください")
	private Integer evaluation;

	private String  comment;
	
}
