package mg.portfolio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminSkillCategoryRequest(
        @NotBlank @Size(max = 120) String name,
        int sortOrder,
        boolean published) {}
