package mg.portfolio.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {
    List<SkillCategory> findByPublishedTrueOrderBySortOrderAsc();
    List<SkillCategory> findAllByOrderBySortOrderAsc();
    java.util.Optional<SkillCategory> findByNameIgnoreCase(String name);
}
