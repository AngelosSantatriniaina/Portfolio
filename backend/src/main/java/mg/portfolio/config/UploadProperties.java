package mg.portfolio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.nio.file.Path;

@ConfigurationProperties(prefix = "app.uploads")
public record UploadProperties(Path directory) {}
