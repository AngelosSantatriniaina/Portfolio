package mg.portfolio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.supabase.storage")
public record SupabaseStorageProperties(
        String url,
        String secretKey,
        String bucket

) {
}
