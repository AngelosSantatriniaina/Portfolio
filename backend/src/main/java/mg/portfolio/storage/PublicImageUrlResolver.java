package mg.portfolio.storage;

import mg.portfolio.config.SupabaseStorageProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PublicImageUrlResolver {

    private static final String UPLOADS_PREFIX = "/uploads/";

    private final SupabaseStorageProperties properties;

    public PublicImageUrlResolver(SupabaseStorageProperties properties) {
        this.properties = properties;
    }

    public String resolve(String path) {
        if (path == null || path.isBlank()) {
            return path;
        }

        String value = path.trim();

        // Une URL déjà publique ou externe ne doit pas être transformée.
        if (value.startsWith("https://") || value.startsWith("http://")) {
            return value;
        }

        // Ne transformer que les anciens chemins locaux /uploads/.
        if (!value.startsWith(UPLOADS_PREFIX)) {
            return value;
        }

        String filename = value.substring(UPLOADS_PREFIX.length());

        if (filename.isBlank() || filename.contains("/")) {
            throw new IllegalArgumentException("Chemin d'image invalide : " + value);
        }

        if (properties.url() == null || properties.url().isBlank()
                || properties.bucket() == null || properties.bucket().isBlank()) {
            throw new IllegalStateException("La configuration Supabase Storage est incomplète.");
        }

        return UriComponentsBuilder.fromUriString(properties.url())
                .pathSegment("storage", "v1", "object", "public",
                        properties.bucket(), filename)
                .build()
                .encode()
                .toUriString();
    }
}