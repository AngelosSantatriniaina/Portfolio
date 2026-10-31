package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="experiences")
public class Experience {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=180) private String title;
 @Column(length=180) private String organization;
 @Column(length=180) private String location;
 @Column(name="period_label",length=100) private String period;
 @Column(length=1000) private String summary;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false) private boolean published=true;
 protected Experience() {}
 public Long getId(){return id;} public String getTitle(){return title;} public String getOrganization(){return organization;}
 public String getLocation(){return location;} public String getPeriod(){return period;} public String getSummary(){return summary;}
 public int getSortOrder(){return sortOrder;} public boolean isPublished(){return published;}
 public Experience(String title,String organization,String location,String period,String summary,int sortOrder,boolean published){
  this.title=title;this.organization=organization;this.location=location;this.period=period;this.summary=summary;this.sortOrder=sortOrder;this.published=published;
 }
 public void update(String title,String organization,String location,String period,String summary,int sortOrder,boolean published){
  this.title=title;this.organization=organization;this.location=location;this.period=period;this.summary=summary;this.sortOrder=sortOrder;this.published=published;
 }
}
