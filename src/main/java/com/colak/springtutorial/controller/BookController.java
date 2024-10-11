package com.colak.springtutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {

    @GetMapping("/api/books")
    public List<String> getBooks(@RequestParam boolean shouldFail) {
        // If shouldFail is true, throw an exception
        if (shouldFail) {
            throw new RuntimeException("Simulated service failure due to parameter.");
        }
        return Arrays.asList("Book 1", "Book 2", "Book 3");
    }

}
