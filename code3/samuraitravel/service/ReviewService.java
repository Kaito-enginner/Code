package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewForm;
import com.example.samuraitravel.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void create(ReviewForm reviewForm) {
		Review review = new Review();
		
		review.setHouse(reviewForm.getHouseId());
		review.setUser(reviewForm.getUserId());
		review.setEvaluation(reviewForm.getEvaluation());
		review.setComment(reviewForm.getComment());
		
		reviewRepository.save(review);
	}
}
