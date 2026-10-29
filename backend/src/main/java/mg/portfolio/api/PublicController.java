package mg.portfolio.api;

import mg.portfolio.api.dto.*;
import mg.portfolio.common.ResourceNotFoundException;
import mg.portfolio.domain.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
 private final ProfileRepository profiles; private final ExperienceRepository experiences;
 private final SkillRepository skills; private final PortfolioServiceRepository services; private final ProjectRepository projects;
 public PublicController(ProfileRepository profiles,ExperienceRepository experiences,SkillRepository skills,PortfolioServiceRepository services,ProjectRepository projects){
  this.profiles=profiles;this.experiences=experiences;this.skills=skills;this.services=services;this.projects=projects;
 }
 @GetMapping("/profile") public ProfileDto profile(){return profiles.findById(1L).map(ProfileDto::from).orElseThrow(()->new ResourceNotFoundException("Profil non configuré"));}
 @GetMapping("/experiences") public List<ExperienceDto> experiences(){return experiences.findByPublishedTrueOrderBySortOrderAsc().stream().map(ExperienceDto::from).toList();}
 @GetMapping("/skills") public List<SkillDto> skills(){return skills.findByPublishedTrueOrderBySortOrderAsc().stream().map(SkillDto::from).toList();}
 @GetMapping("/services") public List<ServiceDto> services(){return services.findByPublishedTrueOrderBySortOrderAsc().stream().map(ServiceDto::from).toList();}
 @GetMapping("/projects") public List<ProjectDto> projects(){return projects.findByPublishedTrueOrderBySortOrderAsc().stream().map(ProjectDto::from).toList();}
}
