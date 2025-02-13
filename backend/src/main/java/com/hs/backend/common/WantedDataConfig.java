package com.hs.backend.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "most-wanted-config")
public class WantedDataConfig {
    private String jwtSecret;
    private String jwtIssuer;
    private Long jwtExpiresIn;
    private String fbiWantedBaseUrl;
    private int cacheDuration;
    private int maxRetries;
    private long retryDelay;
}

