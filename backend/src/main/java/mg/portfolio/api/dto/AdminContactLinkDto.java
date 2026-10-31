package mg.portfolio.api.dto;

import mg.portfolio.domain.ContactLink;

public record AdminContactLinkDto(Long id, String label, String url, String icon, int sortOrder, boolean published) {
    public static AdminContactLinkDto from(ContactLink item) {
        return new AdminContactLinkDto(item.getId(), item.getLabel(), item.getUrl(), item.getIcon(),
                item.getSortOrder(), item.isPublished());
    }
}
