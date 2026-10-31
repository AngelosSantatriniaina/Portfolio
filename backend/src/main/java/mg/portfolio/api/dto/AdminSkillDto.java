package mg.portfolio.api.dto;

import mg.portfolio.domain.Skill;

public record AdminSkillDto(Long id, String name, String category, int sortOrder, int level, boolean published) {
    public static AdminSkillDto from(Skill item) {
        return new AdminSkillDto(item.getId(), item.getName(), item.getCategory().getName(),
                item.getSortOrder(), item.getLevel(), item.isPublished());
    }
}
