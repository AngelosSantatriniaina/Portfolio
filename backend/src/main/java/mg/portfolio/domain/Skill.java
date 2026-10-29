package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="skills")
public class Skill {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="category_id") private SkillCategory category;
 @Column(nullable=false,length=120) private String name;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false) private boolean published=true;
 protected Skill() {}
 public Long getId(){return id;} public SkillCategory getCategory(){return category;} public String getName(){return name;}
 public int getSortOrder(){return sortOrder;} public boolean isPublished(){return published;}
}
