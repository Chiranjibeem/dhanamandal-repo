package com.dhanmandal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DhanmandalController {

    @GetMapping("/hello/{name}")
    public String getMessage(@PathVariable String name){
        return "HI "+name;

    }
}
