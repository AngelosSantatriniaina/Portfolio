package mg.portfolio.api;

import mg.portfolio.api.dto.ProjectDto;
import mg.portfolio.common.ResourceNotFoundException;
import mg.portfolio.domain.Project;
import mg.portfolio.domain.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/projects")
public class AdminProjectController {
 private final ProjectRepository projects;
 public AdminProjectController(ProjectRepository projects){this.projects=projects;}
 @GetMapping public List<ProjectDto> all(){return projects.findAllByOrderBySortOrderAsc().stream().map(ProjectDto::from).toList();}
 @PostMapping public ProjectDto create(@Valid @RequestBody ProjectDto dto){
  Project p=new Project(dto.title().trim(),dto.description().trim(),blankToNull(dto.repositoryUrl()),blankToNull(dto.demoUrl()),dto.year(),dto.sortOrder(),dto.featured(),dto.published());
  return ProjectDto.from(projects.save(p));
 }
 @PutMapping("/{id}") public ProjectDto update(@PathVariable Long id,@Valid @RequestBody ProjectDto dto){
  Project p=projects.findById(id).orElseThrow(()->new ResourceNotFoundException("Projet introuvable: "+id));
  p.update(dto.title().trim(),dto.description().trim(),blankToNull(dto.repositoryUrl()),blankToNull(dto.demoUrl()),dto.year(),dto.sortOrder(),dto.featured(),dto.published());
  return ProjectDto.from(projects.save(p));
 }
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){
  if(!projects.existsById(id)) throw new ResourceNotFoundException("Projet introuvable: "+id);
  projects.deleteById(id);
 }
 private String blankToNull(String s){return s==null||s.isBlank()?null:s.trim();}
}
