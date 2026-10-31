package mg.portfolio.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    List<ProjectImage> findByProjectIdOrderBySortOrderAscIdAsc(Long projectId);
    void deleteByProjectId(Long projectId);
}
