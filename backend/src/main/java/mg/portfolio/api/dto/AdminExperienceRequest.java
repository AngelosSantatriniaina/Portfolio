package mg.portfolio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminExperienceRequest(
        @NotBlank @Size(max = 180) String title,
        @Size(max = 180) String organization,
        @Size(max = 180) String location,
        @Size(max = 100) String period,
        @Size(max = 1000) String summary,
        int sortOrder,
        boolean published) {}
