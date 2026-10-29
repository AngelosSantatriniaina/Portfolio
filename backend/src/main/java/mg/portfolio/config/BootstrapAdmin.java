package mg.portfolio.config;

import mg.portfolio.auth.AdminUser;
import mg.portfolio.auth.AdminUserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Configuration
public class BootstrapAdmin {
    @Bean
    ApplicationRunner createInitialAdmin(AdminProperties props, AdminUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() > 0) return;
            if (!StringUtils.hasText(props.email()) || !StringUtils.hasText(props.password())) {
                throw new IllegalStateException("Première initialisation : définir ADMIN_EMAIL et ADMIN_PASSWORD.");
            }
            if (props.password().length() < 14) {
                throw new IllegalStateException("ADMIN_PASSWORD doit contenir au moins 14 caractères.");
            }
            repo.save(new AdminUser(props.email().trim().toLowerCase(), encoder.encode(props.password())));
        };
    }
}
