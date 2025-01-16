package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.repository.ReviewRepository;

@Controller
@RequestMapping("/houses/{id}")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	
	public ReviewController(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@GetMapping("/review")
	public String index(@PathVariable(name = "id") Integer id, Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<Review> reviewpage = reviewRepository.findByHouse(id, pageable);
		
		model.addAttribute("reviewpage",reviewpage);
		
		return "review/index";
	}
}
