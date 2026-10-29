package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="skill_categories")
public class SkillCategory {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=120) private String name;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false) private boolean published=true;
 protected SkillCategory() {}
 public Long getId(){return id;} public String getName(){return name;} public int getSortOrder(){return sortOrder;} public boolean isPublished(){return published;}
}
