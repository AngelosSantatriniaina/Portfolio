package mg.portfolio.api.dto;
import mg.portfolio.domain.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record ProjectDto(Long id, @NotBlank @Size(max=180) String title,
 @NotBlank @Size(max=2000) String description, @Size(max=500) String repositoryUrl,
 @Size(max=500) String demoUrl, Integer year, int sortOrder, boolean featured, boolean published, String imagePath) {
 public static ProjectDto from(Project p){return new ProjectDto(p.getId(),p.getTitle(),p.getDescription(),p.getRepositoryUrl(),p.getDemoUrl(),p.getYear(),p.getSortOrder(),p.isFeatured(),p.isPublished(),p.getImagePath());}
}
