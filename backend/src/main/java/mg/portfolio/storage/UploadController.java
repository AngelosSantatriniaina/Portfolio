
package mg.portfolio.storage;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/uploads")
public class UploadController {

    private static final Set<String> ALLOWED_TYPES =
            Set.of("image/jpeg", "image/png");

    private final SupabaseStorageService storageService;

    public UploadController(SupabaseStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadImage(
            @RequestPart("file") MultipartFile file
    ) throws IOException {

        // 1. Vérifier que le fichier existe.
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide.");
        }

        // 2. Vérifier le type MIME.
        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Formats autorisés : JPEG et PNG."
            );
        }

        // 3. Lire les octets du fichier.
        byte[] fileBytes = file.getBytes();

        // 4. Vérifier le contenu réel de l'image.
        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(fileBytes)
        );

        if (image == null) {
            throw new IllegalArgumentException(
                    "Le fichier n'est pas une image JPEG/PNG valide."
            );
        }

        // 5. Vérifier les dimensions.
        if (image.getWidth() > 8000 || image.getHeight() > 8000) {
            throw new IllegalArgumentException(
                    "Dimensions d'image trop grandes (maximum 8000 × 8000)."
            );
        }

        // 6. Générer un nom unique.
        String extension = "image/png".equals(contentType)
                ? ".png"
                : ".jpg";

        String filename = UUID.randomUUID() + extension;

        // 7. Envoyer l'image vers Supabase Storage.
        String publicUrl = storageService.upload(
                filename,
                contentType,
                fileBytes
        );

        // 8. Retourner une réponse compatible avec React.
        return Map.of(
                "path", publicUrl,
                "filename", filename
        );
    }
}
