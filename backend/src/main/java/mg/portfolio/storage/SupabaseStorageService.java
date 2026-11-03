package mg.portfolio.storage;

import mg.portfolio.config.SupabaseStorageProperties;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class SupabaseStorageService {

    private final SupabaseStorageProperties properties;
    private final RestClient restClient;

    public SupabaseStorageService(
            SupabaseStorageProperties properties,
            RestClient.Builder restClientBuilder
    ) {
        this.properties = properties;
        this.restClient = restClientBuilder.build();
    }

    public String upload(String filename, String contentType, byte[] fileBytes) {

        // Vérifier la configuration avant l'envoi.
        if (properties.url() == null || properties.url().isBlank()
                || properties.secretKey() == null || properties.secretKey().isBlank()
                || properties.bucket() == null || properties.bucket().isBlank()) {
            throw new IllegalStateException(
                    "La configuration Supabase Storage est incomplète."
            );
        }

        // Construire l'adresse de destination dans Supabase Storage.
        URI uploadUri = UriComponentsBuilder
                .fromUriString(properties.url())
                .pathSegment(
                        "storage",
                        "v1",
                        "object",
                        properties.bucket(),
                        filename
                )
                .build()
                .encode()
                .toUri();

        // Envoyer les octets vers Supabase.
        restClient.post()
                .uri(uploadUri)
                .header("apikey", properties.secretKey())
                .header("x-upsert", "false")
                .contentType(MediaType.parseMediaType(contentType))
                .body(fileBytes)
                .retrieve()
                .toBodilessEntity();

        // Construire l'URL publique de l'image.
        return UriComponentsBuilder
                .fromUriString(properties.url())
                .pathSegment(
                        "storage",
                        "v1",
                        "object",
                        "public",
                        properties.bucket(),
                        filename
                )
                .build()
                .encode()
                .toUriString();
    }
}