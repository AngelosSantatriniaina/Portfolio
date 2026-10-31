package mg.portfolio.api;

import jakarta.validation.Valid;
import mg.portfolio.api.dto.*;
import mg.portfolio.common.ResourceNotFoundException;
import mg.portfolio.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminContentController {
    private final ProfileRepository profiles;
    private final AboutItemRepository aboutItems;
    private final ExperienceRepository experiences;
    private final SkillRepository skills;
    private final SkillCategoryRepository skillCategories;
    private final PortfolioServiceRepository services;
    private final ContactLinkRepository contactLinks;
    private final ReviewRepository reviews;
    private final ContactMessageRepository messages;

    public AdminContentController(ProfileRepository profiles, AboutItemRepository aboutItems, ExperienceRepository experiences, SkillRepository skills,
                                  SkillCategoryRepository skillCategories, PortfolioServiceRepository services,
                                  ContactLinkRepository contactLinks, ReviewRepository reviews,
                                  ContactMessageRepository messages) {
        this.profiles = profiles;
        this.aboutItems = aboutItems;
        this.experiences = experiences;
        this.skills = skills;
        this.skillCategories = skillCategories;
        this.services = services;
        this.contactLinks = contactLinks;
        this.reviews = reviews;
        this.messages = messages;
    }

    @GetMapping("/profile")
    public ProfileDto profile() {
        return profiles.findById(1L).map(ProfileDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Profil non configuré"));
    }

    @PutMapping("/profile")
    public ProfileDto updateProfile(@Valid @RequestBody AdminProfileRequest request) {
        Profile profile = profiles.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Profil non configuré"));
        profile.update(request.fullName().trim(), request.headline().trim(), blankToNull(request.bio()),
                blankToNull(request.location()), blankToNull(request.email()), blankToNull(request.githubUrl()),
                blankToNull(request.linkedinUrl()), blankToNull(request.whatsappUrl()), blankToNull(request.portraitPath()));
        return ProfileDto.from(profiles.save(profile));
    }

    @GetMapping("/about-items")
    public List<AdminAboutItemDto> aboutItems() {
        return aboutItems.findAllByOrderBySortOrderAsc().stream().map(AdminAboutItemDto::from).toList();
    }

    @PostMapping("/about-items")
    public AdminAboutItemDto createAboutItem(@Valid @RequestBody AdminAboutItemRequest request) {
        return AdminAboutItemDto.from(aboutItems.save(new AboutItem(request.title().trim(),
                request.description().trim(), blankToNull(request.icon()), request.sortOrder(), request.published())));
    }

    @PutMapping("/about-items/{id}")
    public AdminAboutItemDto updateAboutItem(@PathVariable Long id, @Valid @RequestBody AdminAboutItemRequest request) {
        AboutItem item = aboutItems.findById(id).orElseThrow(() -> notFound("Élément À propos", id));
        item.update(request.title().trim(), request.description().trim(), blankToNull(request.icon()),
                request.sortOrder(), request.published());
        return AdminAboutItemDto.from(aboutItems.save(item));
    }

    @DeleteMapping("/about-items/{id}")
    public void deleteAboutItem(@PathVariable Long id) {
        aboutItems.delete(aboutItems.findById(id).orElseThrow(() -> notFound("Élément À propos", id)));
    }

    @GetMapping("/experiences")
    public List<AdminExperienceDto> experiences() {
        return experiences.findAllByOrderBySortOrderAsc().stream().map(AdminExperienceDto::from).toList();
    }

    @PostMapping("/experiences")
    public AdminExperienceDto createExperience(@Valid @RequestBody AdminExperienceRequest request) {
        return AdminExperienceDto.from(experiences.save(new Experience(request.title().trim(),
                blankToNull(request.organization()), blankToNull(request.location()), blankToNull(request.period()),
                blankToNull(request.summary()), request.sortOrder(), request.published())));
    }

    @PutMapping("/experiences/{id}")
    public AdminExperienceDto updateExperience(@PathVariable Long id, @Valid @RequestBody AdminExperienceRequest request) {
        Experience item = experiences.findById(id).orElseThrow(() -> notFound("Expérience", id));
        item.update(request.title().trim(), blankToNull(request.organization()), blankToNull(request.location()),
                blankToNull(request.period()), blankToNull(request.summary()), request.sortOrder(), request.published());
        return AdminExperienceDto.from(experiences.save(item));
    }

    @DeleteMapping("/experiences/{id}")
    public void deleteExperience(@PathVariable Long id) {
        experiences.delete(experiences.findById(id).orElseThrow(() -> notFound("Expérience", id)));
    }

    @GetMapping("/skills")
    public List<AdminSkillDto> skills() {
        return skills.findAllWithCategoryOrderBySortOrderAsc().stream().map(AdminSkillDto::from).toList();
    }

    @PostMapping("/skills")
    public AdminSkillDto createSkill(@Valid @RequestBody AdminSkillRequest request) {
        SkillCategory category = resolveCategory(request.category());
        return AdminSkillDto.from(skills.save(new Skill(category, request.name().trim(),
                request.sortOrder(), request.level(), request.published())));
    }

    @PutMapping("/skills/{id}")
    public AdminSkillDto updateSkill(@PathVariable Long id, @Valid @RequestBody AdminSkillRequest request) {
        Skill item = skills.findById(id).orElseThrow(() -> notFound("Compétence", id));
        item.update(resolveCategory(request.category()), request.name().trim(), request.sortOrder(), request.level(), request.published());
        return AdminSkillDto.from(skills.save(item));
    }

    @DeleteMapping("/skills/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skills.delete(skills.findById(id).orElseThrow(() -> notFound("Compétence", id)));
    }

    @GetMapping("/skill-categories")
    public List<AdminSkillCategoryDto> skillCategories() {
        return skillCategories.findAllByOrderBySortOrderAsc().stream().map(AdminSkillCategoryDto::from).toList();
    }

    @PostMapping("/skill-categories")
    public AdminSkillCategoryDto createSkillCategory(@Valid @RequestBody AdminSkillCategoryRequest request) {
        return AdminSkillCategoryDto.from(skillCategories.save(new SkillCategory(request.name().trim(),
                request.sortOrder(), request.published())));
    }

    @PutMapping("/skill-categories/{id}")
    @Transactional
    public AdminSkillCategoryDto updateSkillCategory(@PathVariable Long id, @Valid @RequestBody AdminSkillCategoryRequest request) {
        SkillCategory category = skillCategories.findById(id).orElseThrow(() -> notFound("Catégorie", id));
        category.update(request.name().trim(), request.sortOrder(), request.published());
        return AdminSkillCategoryDto.from(skillCategories.save(category));
    }

    @DeleteMapping("/skill-categories/{id}")
    @Transactional
    public void deleteSkillCategory(@PathVariable Long id) {
        SkillCategory category = skillCategories.findById(id).orElseThrow(() -> notFound("Catégorie", id));
        skills.deleteAll(skills.findAllWithCategoryOrderBySortOrderAsc().stream()
                .filter(skill -> skill.getCategory().getId().equals(id)).toList());
        skillCategories.delete(category);
    }

    @GetMapping("/services")
    public List<AdminServiceDto> services() {
        return services.findAllByOrderBySortOrderAsc().stream().map(AdminServiceDto::from).toList();
    }

    @PostMapping("/services")
    public AdminServiceDto createService(@Valid @RequestBody AdminServiceRequest request) {
        return AdminServiceDto.from(services.save(new PortfolioService(request.title().trim(),
                request.description().trim(), blankToNull(request.icon()), request.sortOrder(), request.published())));
    }

    @PutMapping("/services/{id}")
    public AdminServiceDto updateService(@PathVariable Long id, @Valid @RequestBody AdminServiceRequest request) {
        PortfolioService item = services.findById(id).orElseThrow(() -> notFound("Service", id));
        item.update(request.title().trim(), request.description().trim(), blankToNull(request.icon()),
                request.sortOrder(), request.published());
        return AdminServiceDto.from(services.save(item));
    }

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable Long id) {
        services.delete(services.findById(id).orElseThrow(() -> notFound("Service", id)));
    }

    @GetMapping("/contact-links")
    public List<AdminContactLinkDto> contactLinks() {
        return contactLinks.findAllByOrderBySortOrderAsc().stream().map(AdminContactLinkDto::from).toList();
    }

    @PostMapping("/contact-links")
    public AdminContactLinkDto createContactLink(@Valid @RequestBody AdminContactLinkRequest request) {
        return AdminContactLinkDto.from(contactLinks.save(new ContactLink(request.label().trim(), request.url().trim(),
                blankToNull(request.icon()), request.sortOrder(), request.published())));
    }

    @PutMapping("/contact-links/{id}")
    public AdminContactLinkDto updateContactLink(@PathVariable Long id, @Valid @RequestBody AdminContactLinkRequest request) {
        ContactLink item = contactLinks.findById(id).orElseThrow(() -> notFound("Lien de contact", id));
        item.update(request.label().trim(), request.url().trim(), blankToNull(request.icon()),
                request.sortOrder(), request.published());
        return AdminContactLinkDto.from(contactLinks.save(item));
    }

    @DeleteMapping("/contact-links/{id}")
    public void deleteContactLink(@PathVariable Long id) {
        contactLinks.delete(contactLinks.findById(id).orElseThrow(() -> notFound("Lien de contact", id)));
    }

    @GetMapping("/reviews")
    public List<AdminReviewDto> reviews() {
        return reviews.findAllByOrderByIdDesc().stream().map(AdminReviewDto::from).toList();
    }

    @PostMapping("/reviews")
    public AdminReviewDto createReview(@Valid @RequestBody AdminReviewRequest request) {
        return AdminReviewDto.from(reviews.save(new Review(request.authorName().trim(), request.content().trim(),
                request.rating(), request.published())));
    }

    @PutMapping("/reviews/{id}")
    public AdminReviewDto updateReview(@PathVariable Long id, @Valid @RequestBody AdminReviewRequest request) {
        Review item = reviews.findById(id).orElseThrow(() -> notFound("Témoignage", id));
        item.update(request.authorName().trim(), request.content().trim(), request.rating(), request.published());
        return AdminReviewDto.from(reviews.save(item));
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviews.delete(reviews.findById(id).orElseThrow(() -> notFound("Témoignage", id)));
    }

    @GetMapping("/messages")
    public List<AdminMessageDto> messages() {
        return messages.findAllByOrderByCreatedAtDesc().stream().map(AdminMessageDto::from).toList();
    }

    @PatchMapping("/messages/{id}/read")
    public AdminMessageDto markMessageRead(@PathVariable Long id) {
        ContactMessage message = messages.findById(id).orElseThrow(() -> notFound("Message", id));
        message.markRead();
        return AdminMessageDto.from(messages.save(message));
    }

    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messages.delete(messages.findById(id).orElseThrow(() -> notFound("Message", id)));
    }

    private SkillCategory resolveCategory(String name) {
        String normalized = name.trim();
        return skillCategories.findByNameIgnoreCase(normalized)
                .orElseGet(() -> skillCategories.save(new SkillCategory(normalized, 0, true)));
    }

    private ResourceNotFoundException notFound(String type, Long id) {
        return new ResourceNotFoundException(type + " introuvable: " + id);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
