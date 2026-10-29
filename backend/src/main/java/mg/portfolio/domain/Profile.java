package mg.portfolio.domain;
import jakarta.persistence.*;
@Entity @Table(name="profiles")
public class Profile {
 @Id private Long id = 1L;
 @Column(nullable=false,length=180) private String fullName;
 @Column(nullable=false,length=180) private String headline;
 @Column(length=1000) private String bio;
 @Column(length=160) private String location;
 @Column(length=255) private String email;
 @Column(length=255) private String githubUrl;
 @Column(length=255) private String whatsappUrl;
 @Column(length=255) private String portraitPath;
 protected Profile() {}
 public Long getId(){return id;} public String getFullName(){return fullName;} public String getHeadline(){return headline;}
 public String getBio(){return bio;} public String getLocation(){return location;} public String getEmail(){return email;}
 public String getGithubUrl(){return githubUrl;} public String getWhatsappUrl(){return whatsappUrl;} public String getPortraitPath(){return portraitPath;}
}
