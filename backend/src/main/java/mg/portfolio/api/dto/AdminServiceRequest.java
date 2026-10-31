package mg.portfolio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminServiceRequest(
        @NotBlank @Size(max = 180) String title,
        @NotBlank @Size(max = 1000) String description,
        @Size(max = 80) String icon,
        int sortOrder,
        boolean published) {}
