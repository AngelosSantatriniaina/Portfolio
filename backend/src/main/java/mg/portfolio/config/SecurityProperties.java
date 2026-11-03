package mg.portfolio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties(String jwtSecretBase64, long jwtTtlMinutes,
        boolean cookieSecure, String cookieName,
        String allowedOrigins) {
}
