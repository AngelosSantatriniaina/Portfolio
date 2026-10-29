package mg.portfolio.api.dto;
import mg.portfolio.domain.PortfolioService;
public record ServiceDto(Long id,String title,String description,String icon,int sortOrder) {
 public static ServiceDto from(PortfolioService s){return new ServiceDto(s.getId(),s.getTitle(),s.getDescription(),s.getIcon(),s.getSortOrder());}
}
