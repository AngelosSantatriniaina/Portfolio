package mg.portfolio.api.dto;

import mg.portfolio.domain.ContactMessage;

import java.time.Instant;

public record AdminMessageDto(Long id, String name, String email, String subject, String message,
                              Instant createdAt, boolean readByAdmin) {
    public static AdminMessageDto from(ContactMessage item) {
        return new AdminMessageDto(item.getId(), item.getName(), item.getEmail(), item.getSubject(),
                item.getMessage(), item.getCreatedAt(), item.isReadByAdmin());
    }
}
