package mg.portfolio.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactLinkRepository extends JpaRepository<ContactLink, Long> {
    List<ContactLink> findByPublishedTrueOrderBySortOrderAsc();
}
