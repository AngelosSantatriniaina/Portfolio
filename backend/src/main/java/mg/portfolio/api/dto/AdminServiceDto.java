package mg.portfolio.api.dto;

import mg.portfolio.domain.PortfolioService;

public record AdminServiceDto(Long id, String title, String description, String icon, int sortOrder, boolean published) {
    public static AdminServiceDto from(PortfolioService item) {
        return new AdminServiceDto(item.getId(), item.getTitle(), item.getDescription(), item.getIcon(),
                item.getSortOrder(), item.isPublished());
    }
}
