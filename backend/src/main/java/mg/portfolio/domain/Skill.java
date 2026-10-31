package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="skills")
public class Skill {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="category_id") private SkillCategory category;
 @Column(nullable=false,length=120) private String name;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false) private int level=75;
 @Column(nullable=false) private boolean published=true;
 protected Skill() {}
 public Long getId(){return id;} public SkillCategory getCategory(){return category;} public String getName(){return name;}
 public int getSortOrder(){return sortOrder;} public int getLevel(){return level;} public boolean isPublished(){return published;}
 public Skill(SkillCategory category,String name,int sortOrder,int level,boolean published){this.category=category;this.name=name;this.sortOrder=sortOrder;this.level=level;this.published=published;}
 public void update(SkillCategory category,String name,int sortOrder,int level,boolean published){this.category=category;this.name=name;this.sortOrder=sortOrder;this.level=level;this.published=published;}
}
