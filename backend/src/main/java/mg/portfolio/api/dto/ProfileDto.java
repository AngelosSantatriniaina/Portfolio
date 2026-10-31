package mg.portfolio.api.dto;
import mg.portfolio.domain.Profile;
public record ProfileDto(Long id,String fullName,String headline,String bio,String location,String email,String githubUrl,String linkedinUrl,String whatsappUrl,String portraitPath) {
 public static ProfileDto from(Profile p){return new ProfileDto(p.getId(),p.getFullName(),p.getHeadline(),p.getBio(),p.getLocation(),p.getEmail(),p.getGithubUrl(),p.getLinkedinUrl(),p.getWhatsappUrl(),p.getPortraitPath());}
}
