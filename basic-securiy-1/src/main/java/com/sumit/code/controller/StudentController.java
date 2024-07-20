package com.sumit.code.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sumit.code.model.Student;

@RestController
public class StudentController {
	private List<Student> students=new ArrayList<>();
	
	@GetMapping("/students")
	public List<Student> students(){
	
		
		Student student1=new Student();
		student1.setId(1);
		student1.setFirstName("sumit");
		student1.setLastName("dhanorkar");
		
		Student student2=new Student();
		student2.setId(2);
		student2.setFirstName("Niraj");
		student2.setLastName("Tiwari");
		
		students.add(student1);
		students.add(student2);
		return students;
		
	}
	
	
	@PostMapping("/addStudent")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Student> createStudent(@RequestBody Student student) {
		students.add(student);
		return students;
	}
	
	
	
}
