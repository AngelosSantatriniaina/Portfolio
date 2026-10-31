package mg.portfolio.api.dto;

import mg.portfolio.domain.Experience;

public record AdminExperienceDto(Long id, String title, String organization, String location, String period,
                                 String summary, int sortOrder, boolean published) {
    public static AdminExperienceDto from(Experience item) {
        return new AdminExperienceDto(item.getId(), item.getTitle(), item.getOrganization(), item.getLocation(),
                item.getPeriod(), item.getSummary(), item.getSortOrder(), item.isPublished());
    }
}
