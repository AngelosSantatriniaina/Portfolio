package mg.portfolio.api.dto;

import mg.portfolio.domain.Project;
import mg.portfolio.storage.PublicImageUrlResolver;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProjectDto(
        Long id,
        @NotBlank @Size(max = 180) String title,
        @NotBlank @Size(max = 2000) String description,
        @NotBlank @Size(max = 120) String category,
        @Size(max = 500) String repositoryUrl,
        @Size(max = 500) String demoUrl,
        Integer year,
        int sortOrder,
        boolean featured,
        boolean published,
        @Size(max = 255) String imagePath,
        List<@Size(max = 100) String> technologies,
        List<@Size(max = 255) String> imagePaths
) {
    public static ProjectDto from(
            Project p,
            List<String> technologies,
            List<String> imagePaths,
            PublicImageUrlResolver imageUrls
    ) {
        List<String> paths = imagePaths.isEmpty() && p.getImagePath() != null
                ? List.of(p.getImagePath())
                : imagePaths;

        return new ProjectDto(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getCategory(),
                p.getRepositoryUrl(),
                p.getDemoUrl(),
                p.getYear(),
                p.getSortOrder(),
                p.isFeatured(),
                p.isPublished(),
                imageUrls.resolve(p.getImagePath()),
                technologies,
                paths.stream().map(imageUrls::resolve).toList()
        );
    }
}