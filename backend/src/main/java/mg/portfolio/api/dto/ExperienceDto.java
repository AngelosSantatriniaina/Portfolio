package mg.portfolio.api.dto;
import mg.portfolio.domain.Experience;
public record ExperienceDto(Long id,String title,String organization,String location,String period,String summary,int sortOrder) {
 public static ExperienceDto from(Experience e){return new ExperienceDto(e.getId(),e.getTitle(),e.getOrganization(),e.getLocation(),e.getPeriod(),e.getSummary(),e.getSortOrder());}
}
