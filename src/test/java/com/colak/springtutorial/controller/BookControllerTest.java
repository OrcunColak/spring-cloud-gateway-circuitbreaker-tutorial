package com.colak.springtutorial.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/books";

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Test
    void testSuccess() throws Exception {
        // Act & Assert
        mockMvc.perform(get(BASE_URL + "?shouldFail=false")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("Book 1"))
                .andExpect(jsonPath("$[1]").value("Book 2"))
                .andExpect(jsonPath("$[2]").value("Book 3"));
    }

    @Test
    void testFailure() throws Exception {
        // Simulate failure to trigger the circuit breaker
        for (int i = 0; i < 3; i++) {
            try {
                mockMvc.perform(get(BASE_URL + "?shouldFail=true")
                        .accept(MediaType.APPLICATION_JSON));
            } catch (Exception exception) {
            }

        }

        // Wait for the circuit breaker to open
        // You should adjust this duration based on your circuit breaker's waitDurationInOpenState
        Thread.sleep(6000); // Example: wait for 6 seconds if your config is set for 5 seconds

        // Now that we have failed 3 times, the circuit breaker should be open.
        // So, we expect the fallback response for the next request.
        mockMvc.perform(get(BASE_URL + "?shouldFail=true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Service is temporarily unavailable. Please try again later."));
    }
}
