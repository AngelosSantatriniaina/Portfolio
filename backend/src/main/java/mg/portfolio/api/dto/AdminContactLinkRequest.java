package mg.portfolio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminContactLinkRequest(
        @NotBlank @Size(max = 100) String label,
        @NotBlank @Size(max = 500) String url,
        @Size(max = 80) String icon,
        int sortOrder,
        boolean published) {}
