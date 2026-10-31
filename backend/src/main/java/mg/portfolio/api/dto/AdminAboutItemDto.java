package mg.portfolio.api.dto;

import mg.portfolio.domain.AboutItem;

public record AdminAboutItemDto(Long id, String title, String description, String icon, int sortOrder, boolean published) {
    public static AdminAboutItemDto from(AboutItem item) {
        return new AdminAboutItemDto(item.getId(), item.getTitle(), item.getDescription(), item.getIcon(),
                item.getSortOrder(), item.isPublished());
    }
}
