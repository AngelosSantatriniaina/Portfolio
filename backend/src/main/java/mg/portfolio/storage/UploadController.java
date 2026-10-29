package mg.portfolio.storage;

import mg.portfolio.config.UploadProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/uploads")
public class UploadController {
    private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png");
    private final Path uploadDir;

    public UploadController(UploadProperties properties) throws IOException {
        this.uploadDir = properties.directory().toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("Le fichier est vide.");
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Formats autorisés : JPEG et PNG.");
        }
        // Contrôle du contenu réel, pas uniquement du nom ou du Content-Type envoyé par le navigateur.
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) throw new IllegalArgumentException("Le fichier n'est pas une image JPEG/PNG valide.");
        if (image.getWidth() > 8000 || image.getHeight() > 8000) {
            throw new IllegalArgumentException("Dimensions d'image trop grandes (maximum 8000 × 8000).");
        }
        String extension = "image/png".equals(file.getContentType()) ? ".png" : ".jpg";
        String filename = UUID.randomUUID() + extension;
        Path destination = uploadDir.resolve(filename).normalize();
        if (!destination.startsWith(uploadDir)) throw new IllegalArgumentException("Chemin de fichier invalide.");
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return Map.of("path", "/uploads/" + filename, "filename", filename);
    }
}
