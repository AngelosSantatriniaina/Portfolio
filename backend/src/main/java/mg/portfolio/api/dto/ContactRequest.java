package mg.portfolio.api.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record ContactRequest(@NotBlank @Size(max=150) String name,
 @NotBlank @Email @Size(max=190) String email,
 @NotBlank @Size(max=180) String subject,
 @NotBlank @Size(max=5000) String message,
 @Size(max=200) String website) {}
