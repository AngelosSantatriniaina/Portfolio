package mg.portfolio.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminReviewRequest(
        @NotBlank @Size(max = 150) String authorName,
        @NotBlank @Size(max = 2000) String content,
        @Min(1) @Max(5) int rating,
        boolean published) {}
