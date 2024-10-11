package com.colak.springtutorial.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testSuccess() {
        // Test for a successful response
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/books?shouldFail=false", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book 1 Book 2 Book 3", response.getBody());
    }


}
