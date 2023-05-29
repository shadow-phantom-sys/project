package com.rideManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RideManagementController {
	
	@GetMapping("/")
	public String showIndexPage() {
		return "index";
	}
}
