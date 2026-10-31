package mg.portfolio.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    private Long id = 1L;
    @Column(nullable = false, length = 180)
    private String fullName;
    @Column(nullable = false, length = 180)
    private String headline;
    @Column(length = 1000)
    private String bio;
    @Column(length = 160)
    private String location;
    @Column(length = 255)
    private String email;
    @Column(length = 255)
    private String githubUrl;
    @Column(length = 255)
    private String whatsappUrl;
    @Column(length = 255)
    private String portraitPath;

    protected Profile() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHeadline() {
        return headline;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getWhatsappUrl() {
        return whatsappUrl;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void update(String fullName, String headline, String bio, String location, String email, String githubUrl,
            String linkedinUrl, String whatsappUrl, String portraitPath) {
        this.fullName = fullName;
        this.headline = headline;
        this.bio = bio;
        this.location = location;
        this.email = email;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.whatsappUrl = whatsappUrl;
        this.portraitPath = portraitPath;
    }

    @Column(name = "linkedin_url", length = 255)
    private String linkedinUrl;

    public String getLinkedinUrl() {
        return linkedinUrl;
    }
}
