package mg.portfolio.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioServiceRepository extends JpaRepository<PortfolioService, Long> {
    List<PortfolioService> findByPublishedTrueOrderBySortOrderAsc();

    List<PortfolioService> findAllByOrderBySortOrderAsc();
}
