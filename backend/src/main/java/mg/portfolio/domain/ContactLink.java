package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="contact_links")
public class ContactLink {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String label;
 @Column(nullable=false,length=500) private String url;
 @Column(length=80) private String icon;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(nullable=false) private boolean published=true;
 protected ContactLink() {}
 public Long getId(){return id;} public String getLabel(){return label;} public String getUrl(){return url;} public String getIcon(){return icon;}
 public int getSortOrder(){return sortOrder;} public boolean isPublished(){return published;}
}