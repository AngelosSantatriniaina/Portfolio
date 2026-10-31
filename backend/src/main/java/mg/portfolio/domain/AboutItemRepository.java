package mg.portfolio.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboutItemRepository extends JpaRepository<AboutItem, Long> {
    List<AboutItem> findByPublishedTrueOrderBySortOrderAsc();
    List<AboutItem> findAllByOrderBySortOrderAsc();
}
