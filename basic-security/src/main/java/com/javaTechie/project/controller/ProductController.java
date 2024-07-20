package com.javaTechie.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaTechie.project.dto.AuthRequest;
import com.javaTechie.project.dto.Product;
import com.javaTechie.project.entity.UserInfo;
import com.javaTechie.project.service.JwtService;
import com.javaTechie.project.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure!";
	}
	
	@PostMapping("/new")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}
	
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts() {
        return this.service.getProducts();
    }
 
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return this.service.getProduct(id);
    }
    
    
    @PostMapping("/authenticate")
    public String authenticateAndGetTokenn(@RequestBody AuthRequest authReq) {
    	
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        if(authenticate.isAuthenticated()) {
        	return jwtService.generateToken(authReq.getUsername());
        }else {
        	throw new UsernameNotFoundException("Invalid user request");
        }
    }
	
	
}
