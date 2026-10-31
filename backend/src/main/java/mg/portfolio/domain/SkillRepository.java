package mg.portfolio.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("select s from Skill s join fetch s.category c where s.published = true and c.published = true order by s.sortOrder asc")
    List<Skill> findByPublishedTrueOrderBySortOrderAsc();
    @Query("select s from Skill s join fetch s.category order by s.sortOrder asc")
    List<Skill> findAllWithCategoryOrderBySortOrderAsc();
}
