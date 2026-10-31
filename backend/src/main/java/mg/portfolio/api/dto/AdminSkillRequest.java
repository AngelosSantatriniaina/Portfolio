package mg.portfolio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AdminSkillRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 120) String category,
        int sortOrder,
        @Min(0) @Max(100) int level,
        boolean published) {}
