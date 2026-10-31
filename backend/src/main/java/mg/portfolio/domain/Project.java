package mg.portfolio.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="projects")
public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=180) private String title;
 @Column(nullable=false,length=2000) private String description;
 @Column(nullable=false,length=120) private String category="Développement";
 @Column(length=255) private String imagePath;
 @Column(name="repository_url",length=500) private String repositoryUrl;
 @Column(name="demo_url",length=500) private String demoUrl;
 @Column(name="project_year") private Integer year;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false) private boolean featured=false;
 @Column(nullable=false) private boolean published=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected Project() {}
 public Project(String title,String description,String category,String repositoryUrl,String demoUrl,String imagePath,Integer year,int sortOrder,boolean featured,boolean published){
   this.title=title;this.description=description;this.category=category;this.repositoryUrl=repositoryUrl;this.demoUrl=demoUrl;this.imagePath=imagePath;this.year=year;this.sortOrder=sortOrder;this.featured=featured;this.published=published;
 }
 public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;} public String getCategory(){return category;}
 public String getImagePath(){return imagePath;} public String getRepositoryUrl(){return repositoryUrl;} public String getDemoUrl(){return demoUrl;}
 public Integer getYear(){return year;} public int getSortOrder(){return sortOrder;} public boolean isFeatured(){return featured;}
 public boolean isPublished(){return published;} public Instant getCreatedAt(){return createdAt;}
 public void update(String title,String description,String category,String repositoryUrl,String demoUrl,String imagePath,Integer year,int sortOrder,boolean featured,boolean published){
   this.title=title;this.description=description;this.category=category;this.repositoryUrl=repositoryUrl;this.demoUrl=demoUrl;this.imagePath=imagePath;this.year=year;this.sortOrder=sortOrder;this.featured=featured;this.published=published;
 }
}
