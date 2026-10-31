package mg.portfolio.api.dto;

import mg.portfolio.domain.SkillCategory;

public record AdminSkillCategoryDto(Long id, String name, int sortOrder, boolean published) {
    public static AdminSkillCategoryDto from(SkillCategory item) {
        return new AdminSkillCategoryDto(item.getId(), item.getName(), item.getSortOrder(), item.isPublished());
    }
}
