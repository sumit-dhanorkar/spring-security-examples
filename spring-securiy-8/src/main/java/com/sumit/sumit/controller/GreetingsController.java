package com.sumit.sumit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GreetingsController {
	
	@GetMapping
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("hello form my api");
	}
	
	@GetMapping("/say-good-bye")
	public ResponseEntity<String> sayGoodBye(){
		return ResponseEntity.ok("good bye for all see you later");
	}


	
}
