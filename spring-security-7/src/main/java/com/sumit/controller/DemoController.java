package com.sumit.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {

    @GetMapping("/demo")
    @PreAuthorize("hasAuthority('read')")
    public String demo(){
        return "hellow sumit";
    }

    //also can be used hasRole() hasAnyAuthority()

    @GetMapping("/demo1")
    @PreAuthorize("hasAnyAuthority('write','read')")
    public String demo2(){
        return "hellow sumit";
    }

    /*
        here the authentication.name is an object from the securityContextHolder
     */

    @GetMapping("/demo3/{babu}")
    @PreAuthorize("#something == authentication.name")
    public String demo3(@PathVariable("babu") String something){

        var a= SecurityContextHolder.getContext().getAuthentication();

        return "demo 3";
    }


    //never user @PostAuthorize with method that change data
    @GetMapping("/demo4")
    @PostAuthorize("returnObject != 'Demo 4'")
    public String demo4(){
        System.out.println("DemoController.demo4");
        return "Demo 4";
    }

    //@PreFilter -> works with only array or collections

    @GetMapping("/demo5")
    @PreFilter("filterObject.contains('a')")
    public String demo5(@RequestBody List<String > values){
        System.out.println("values -> "+values);
        return "Demo 5";
    }


    @GetMapping("/demo6")
    @PostFilter("filterObject.contains('a')")
    public List<String> demo6(){
        List<String > list=new ArrayList<>();
        list.add("asdfg");
        list.add("cvbn");
        list.add("adsfg");
        list.add("cxcvbn");

        //List.of("asdf","werer","cvbn","sdfgadsd") here List.of() is immutable
        return list;
    }
}
