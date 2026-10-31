package mg.portfolio.api;

import mg.portfolio.api.dto.ProjectDto;
import mg.portfolio.common.ResourceNotFoundException;
import mg.portfolio.domain.Project;
import mg.portfolio.domain.ProjectImage;
import mg.portfolio.domain.ProjectImageRepository;
import mg.portfolio.domain.ProjectRepository;
import mg.portfolio.domain.ProjectTechnology;
import mg.portfolio.domain.ProjectTechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/projects")
public class AdminProjectController {
 private final ProjectRepository projects;
 private final ProjectTechnologyRepository technologies;
 private final ProjectImageRepository images;
 public AdminProjectController(ProjectRepository projects,ProjectTechnologyRepository technologies,ProjectImageRepository images){this.projects=projects;this.technologies=technologies;this.images=images;}
 @GetMapping public List<ProjectDto> all(){return projects.findAllByOrderBySortOrderAsc().stream().map(this::toDto).toList();}
 @PostMapping @Transactional public ProjectDto create(@Valid @RequestBody ProjectDto dto){
  List<String> imagePaths=imagePaths(dto);
  Project p=new Project(dto.title().trim(),dto.description().trim(),dto.category().trim(),blankToNull(dto.repositoryUrl()),blankToNull(dto.demoUrl()),firstImage(imagePaths),dto.year(),dto.sortOrder(),dto.featured(),dto.published());
  p=projects.save(p);saveTechnologies(p,dto.technologies());saveImages(p,imagePaths);return toDto(p);
 }
 @PutMapping("/{id}") @Transactional public ProjectDto update(@PathVariable Long id,@Valid @RequestBody ProjectDto dto){
  Project p=projects.findById(id).orElseThrow(()->new ResourceNotFoundException("Projet introuvable: "+id));
  List<String> imagePaths=imagePaths(dto);
  p.update(dto.title().trim(),dto.description().trim(),dto.category().trim(),blankToNull(dto.repositoryUrl()),blankToNull(dto.demoUrl()),firstImage(imagePaths),dto.year(),dto.sortOrder(),dto.featured(),dto.published());
  p=projects.save(p);saveTechnologies(p,dto.technologies());saveImages(p,imagePaths);return toDto(p);
 }
 @DeleteMapping("/{id}") @Transactional public void delete(@PathVariable Long id){
  Project project=projects.findById(id).orElseThrow(()->new ResourceNotFoundException("Projet introuvable: "+id));
  technologies.deleteByProjectId(id);
  images.deleteByProjectId(id);
  projects.delete(project);
 }
 private String blankToNull(String s){return s==null||s.isBlank()?null:s.trim();}
 private String firstImage(List<String> values){return values.isEmpty()?null:values.get(0);}
 private List<String> imagePaths(ProjectDto dto){
  List<String> values=dto.imagePaths();
  if(values==null)values=dto.imagePath()==null?List.of():List.of(dto.imagePath());
  return values.stream().map(this::blankToNull).filter(value->value!=null).distinct().toList();
 }
 private ProjectDto toDto(Project project){return ProjectDto.from(project,
  technologies.findByProjectIdOrderByIdAsc(project.getId()).stream().map(ProjectTechnology::getTechnology).toList(),
  images.findByProjectIdOrderBySortOrderAscIdAsc(project.getId()).stream().map(ProjectImage::getImagePath).toList());}
 private void saveTechnologies(Project project,List<String> values){
  technologies.deleteByProjectId(project.getId());
  if(values!=null)technologies.saveAll(values.stream().map(String::trim).filter(value->!value.isEmpty()).distinct().map(value->new ProjectTechnology(project,value)).toList());
 }
 private void saveImages(Project project,List<String> values){
  images.deleteByProjectId(project.getId());
  images.saveAll(java.util.stream.IntStream.range(0,values.size())
   .mapToObj(index->new ProjectImage(project,values.get(index),index)).toList());
 }
}
