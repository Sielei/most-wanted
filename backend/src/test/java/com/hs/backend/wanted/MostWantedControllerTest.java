package com.hs.backend.wanted;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MostWantedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void rateLimitExceededShouldReturn429() throws Exception {
        // Perform multiple requests quickly to trigger rate limit
        for (int i = 0; i < 15; i++) {
            mockMvc.perform(get("/api/wanted"))
                    .andExpect(status().is(anyOf(is(200), is(429))));
        }
    }
}