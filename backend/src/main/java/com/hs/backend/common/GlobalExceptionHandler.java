package com.hs.backend.common;

import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {RestClientException.class, TimeoutException.class})
    ProblemDetail handleExternalAPICallExceptions(Exception exception) {
        log.error(exception.getMessage(), exception);
        var location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/errors/internal-server-error")
                .build().toUri();
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        problemDetail.setTitle("Server Error");
        problemDetail.setType(location);
        problemDetail.setProperty("timestamp", DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                .format(LocalDateTime.now()));
        return problemDetail;
    }
}
