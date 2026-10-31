package mg.portfolio.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class PortfolioService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String title;
    @Column(nullable = false, length = 1000)
    private String description;
    @Column(length = 80)
    private String icon;
    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
    @Column(nullable = false)
    private boolean published = true;

    protected PortfolioService() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public boolean isPublished() {
        return published;
    }

    public PortfolioService(String title, String description, String icon, int sortOrder, boolean published) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.sortOrder = sortOrder;
        this.published = published;
    }

    public void update(String title, String description, String icon, int sortOrder, boolean published) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.sortOrder = sortOrder;
        this.published = published;
    }
}
