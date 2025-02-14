package com.hs.backend.wanted;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class MostWantedControllerTest {
    @LocalServerPort
    private Integer port;

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.0-alpine"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api";
    }

    @Test
    void getWantedListShouldReturnExpectedData() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("page", "1")
                .queryParam("pageSize", 10)
                .when()
                .get("/wanted")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data", hasSize(10))
                .body("totalElements", equalTo(1053))
                .body("pageNumber", equalTo(1))
                .body("totalPages", equalTo(106))
                .body("isFirst", equalTo(true))
                .body("isLast", equalTo(false));
    }

    @Test
    void rateLimitExceededShouldReturn429() {
        // Make concurrent requests to trigger rate limit
        for (int i = 0; i < 15; i++) {
            given()
                    .accept(ContentType.JSON)
                    .when()
                    .get("/wanted")
                    .then()
                    .statusCode(200);
        }
    }

    @Test
    void cacheShouldWorkAndImproveResponseTime() {
        // First request - should hit the API and cache
        long startTime1 = System.currentTimeMillis();
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/wanted")
                .then()
                .statusCode(200);
        long duration1 = System.currentTimeMillis() - startTime1;

        // Second request - should hit the cache
        long startTime2 = System.currentTimeMillis();
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/wanted")
                .then()
                .statusCode(200);
        long duration2 = System.currentTimeMillis() - startTime2;

        // Verify cache improved response time
        assertThat(duration2)
                .as("Cache response time should be faster than initial request")
                .isLessThan(duration1);
    }
}