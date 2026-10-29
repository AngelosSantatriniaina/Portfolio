package mg.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;

@Configuration
public class UploadWebConfig implements WebMvcConfigurer {
    private final UploadProperties properties;
    public UploadWebConfig(UploadProperties properties) { this.properties = properties; }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path dir = properties.directory().toAbsolutePath().normalize();
        registry.addResourceHandler("/uploads/**").addResourceLocations(dir.toUri().toString());
    }
}
