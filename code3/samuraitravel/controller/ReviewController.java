package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/houses/{id}")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final HouseRepository houseRepository;
	private final ReviewService reviewService;
	
	public ReviewController(ReviewRepository reviewRepository, HouseRepository houseRepository, ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.houseRepository = houseRepository;
		this.reviewService = reviewService;
	}
	
	@GetMapping("/review")
	public String index(@PathVariable(name = "id") Integer id, Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		House house = houseRepository.getReferenceById(id);
		Page<Review> reviewpage = reviewRepository.findByHouse(house, pageable);
		
		model.addAttribute("house", house);
		model.addAttribute("reviewpage",reviewpage);
		
		return "review/index";
	}
	
	@GetMapping("/register")
	public String register(@PathVariable(name = "id") Integer id,  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		House house = houseRepository.getReferenceById(id);
		
		if(userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			Integer userId = user.getId();
			model.addAttribute("userId", userId);
		}
		
		model.addAttribute("house", house);
		model.addAttribute("reviewForm", new ReviewForm());

		return "review/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated ReviewForm reviewForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "/houses/{id}/register";
		}
		
		reviewService.create(reviewForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューの投稿を完了しました。");    

		
		return "redirect:/houses/{id}/review";
	}
}
