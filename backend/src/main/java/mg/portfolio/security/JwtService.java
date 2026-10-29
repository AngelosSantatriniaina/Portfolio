package mg.portfolio.security;

import mg.portfolio.config.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private final SecurityProperties props;
    private final SecretKey key;

    public JwtService(SecurityProperties props) {
        this.props = props;
        try {
            byte[] bytes = Base64.getDecoder().decode(props.jwtSecretBase64());
            if (bytes.length < 32) throw new IllegalStateException("JWT_SECRET_BASE64 doit décoder au moins 32 octets.");
            this.key = Keys.hmacShaKeyFor(bytes);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("JWT_SECRET_BASE64 absent ou invalide. Générer avec: openssl rand -base64 48", ex);
        }
    }

    public String createToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + props.jwtTtlMinutes() * 60_000L);
        return Jwts.builder().subject(subject).issuedAt(now).expiration(expiry).signWith(key).compact();
    }

    public String extractSubject(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
