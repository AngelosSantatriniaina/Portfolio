package mg.portfolio.api.dto;
import mg.portfolio.domain.Skill;
public record SkillDto(Long id,String name,String category,int sortOrder) {
 public static SkillDto from(Skill s){return new SkillDto(s.getId(),s.getName(),s.getCategory().getName(),s.getSortOrder());}
}
