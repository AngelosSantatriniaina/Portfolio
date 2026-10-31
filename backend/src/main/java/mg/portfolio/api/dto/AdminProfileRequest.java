package mg.portfolio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminProfileRequest(
        @NotBlank @Size(max = 180) String fullName,
        @NotBlank @Size(max = 180) String headline,
        @Size(max = 1000) String bio,
        @Size(max = 160) String location,
        @Size(max = 255) String email,
        @Size(max = 255) String githubUrl,
        @Size(max = 255) String linkedinUrl,
        @Size(max = 255) String whatsappUrl,
        @Size(max = 255) String portraitPath) {}
