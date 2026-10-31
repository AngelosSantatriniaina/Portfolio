package mg.portfolio.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "project_images")
public class ProjectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    protected ProjectImage() {}

    public ProjectImage(Project project, String imagePath, int sortOrder) {
        this.project = project;
        this.imagePath = imagePath;
        this.sortOrder = sortOrder;
    }

    public String getImagePath() {
        return imagePath;
    }
}
