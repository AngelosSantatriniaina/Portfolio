package mg.portfolio.api.dto;

import mg.portfolio.domain.Review;

public record AdminReviewDto(Long id, String authorName, String content, int rating, boolean published) {
    public static AdminReviewDto from(Review review) {
        return new AdminReviewDto(review.getId(), review.getAuthorName(), review.getContent(),
                review.getRating(), review.isPublished());
    }
}
