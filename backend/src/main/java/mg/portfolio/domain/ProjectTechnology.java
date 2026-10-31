package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="project_technologies")
public class ProjectTechnology {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="project_id") private Project project;
 @Column(nullable=false,length=100) private String technology;
 protected ProjectTechnology() {}
 public ProjectTechnology(Project project,String technology){this.project=project;this.technology=technology;}
 public Long getId(){return id;} public Project getProject(){return project;} public String getTechnology(){return technology;}
}
