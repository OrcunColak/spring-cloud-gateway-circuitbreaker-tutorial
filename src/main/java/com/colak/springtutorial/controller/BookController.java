package com.colak.springtutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // http://localhost:8080/books?shouldFail=false
    // http://localhost:8080/api/books?shouldFail=false
    @GetMapping
    public String getBooks(@RequestParam boolean shouldFail) {
        // If shouldFail is true, throw an exception
        if (shouldFail) {
            throw new RuntimeException("Simulated service failure due to parameter.");
        }
        return "Book 1 Book 2 Book 3";
    }

}
