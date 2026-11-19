package mg.portfolio.api;

import mg.portfolio.api.dto.*;
import mg.portfolio.storage.PublicImageUrlResolver;
import mg.portfolio.common.ResourceNotFoundException;
import mg.portfolio.domain.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
  private final PublicImageUrlResolver imageUrls;
  private final ProfileRepository profiles;
  private final AboutItemRepository aboutItems;
  private final ExperienceRepository experiences;
  private final SkillRepository skills;
  private final PortfolioServiceRepository services;
  private final ProjectRepository projects;
  private final ProjectTechnologyRepository technologies;
  private final ProjectImageRepository images;
  private final ContactLinkRepository contactLinks;
  private final ReviewRepository reviews;

  public PublicController(ProfileRepository profiles, AboutItemRepository aboutItems, ExperienceRepository experiences,
      SkillRepository skills, PortfolioServiceRepository services, ProjectRepository projects,
      ProjectTechnologyRepository technologies, ProjectImageRepository images, ContactLinkRepository contactLinks,
      ReviewRepository reviews, PublicImageUrlResolver imageUrls) {
    this.profiles = profiles;
    this.aboutItems = aboutItems;
    this.experiences = experiences;
    this.skills = skills;
    this.services = services;
    this.projects = projects;
    this.technologies = technologies;
    this.images = images;
    this.contactLinks = contactLinks;
    this.reviews = reviews;
    this.imageUrls = imageUrls;
  }

  @GetMapping("/profile")
  public ProfileDto profile() {
    return profiles.findById(1L)
        .map(p -> ProfileDto.from(p, imageUrls))
        .orElseThrow(() -> new ResourceNotFoundException("Profil non configuré"));
  }

  @GetMapping("/about-items")
  public List<AdminAboutItemDto> aboutItems() {
    return aboutItems.findByPublishedTrueOrderBySortOrderAsc().stream().map(AdminAboutItemDto::from).toList();
  }

  @GetMapping("/experiences")
  public List<ExperienceDto> experiences() {
    return experiences.findByPublishedTrueOrderBySortOrderAsc().stream().map(ExperienceDto::from).toList();
  }

  @GetMapping("/skills")
  public List<SkillDto> skills() {
    return skills.findByPublishedTrueOrderBySortOrderAsc().stream().map(SkillDto::from).toList();
  }

  @GetMapping("/services")
  public List<ServiceDto> services() {
    return services.findByPublishedTrueOrderBySortOrderAsc().stream().map(ServiceDto::from).toList();
  }

  @GetMapping("/projects")
  public List<ProjectDto> projects() {
    return projects.findByPublishedTrueOrderBySortOrderAsc().stream()
        .map(p -> ProjectDto.from(
            p,
            technologies.findByProjectIdOrderByIdAsc(p.getId())
                .stream()
                .map(ProjectTechnology::getTechnology)
                .toList(),
            images.findByProjectIdOrderBySortOrderAscIdAsc(p.getId())
                .stream()
                .map(ProjectImage::getImagePath)
                .toList(),
            imageUrls))
        .toList();
  }

  @GetMapping("/contact-links")
  public List<ContactLink> contactLinks() {
    return contactLinks.findByPublishedTrueOrderBySortOrderAsc();
  }

  @GetMapping("/reviews")
  public List<AdminReviewDto> reviews() {
    return reviews.findByPublishedTrueOrderByIdDesc().stream().map(AdminReviewDto::from).toList();
  }
}
