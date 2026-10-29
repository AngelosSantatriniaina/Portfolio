package mg.portfolio.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectTechnologyRepository extends JpaRepository<ProjectTechnology, Long> {
    List<ProjectTechnology> findByProjectIdOrderByIdAsc(Long projectId);
}
