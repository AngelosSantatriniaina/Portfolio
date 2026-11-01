package mg.portfolio.auth;

import mg.portfolio.config.SecurityProperties;
import mg.portfolio.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AdminUserRepository admins;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final SecurityProperties props;

    public AuthController(AdminUserRepository admins, PasswordEncoder encoder, JwtService jwt,
            SecurityProperties props) {
        this.admins = admins;
        this.encoder = encoder;
        this.jwt = jwt;
        this.props = props;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        var admin = admins.findByEmailIgnoreCase(request.email().trim())
                .filter(a -> encoder.matches(request.password(), a.getPasswordHash()))
                .orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException(
                        "Identifiants invalides"));
        ResponseCookie cookie = ResponseCookie.from(props.cookieName(), jwt.createToken(admin.getEmail()))
                .httpOnly(true).secure(props.cookieSecure()).sameSite("None").path("/")
                .maxAge(props.jwtTtlMinutes() * 60).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(java.util.Map.of("email", admin.getEmail(), "role", "ADMIN"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from(props.cookieName(), "").httpOnly(true)
                .secure(props.cookieSecure()).sameSite("None").path("/").maxAge(0).build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @GetMapping("/csrf")
    public java.util.Map<String, String> csrf(CsrfToken token) {
        return java.util.Map.of("headerName", token.getHeaderName(), "token", token.getToken());
    }
}
