package ma.youcode.surveyit.util.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private final Key jwtSecretKey;
    private final Long expirationTime;

    public JwtService() {
        Dotenv env = Dotenv.load();
        String tempSecretKey = env.get("JWT_SECRET_KEY");
        Long tempExpirationTime = Long.valueOf(env.get("JWT_EXPIRATION_TIME"));
        this.jwtSecretKey = Keys.hmacShaKeyFor(tempSecretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = tempExpirationTime;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public List<String> extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", List.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role" , userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return createToken(claims, userDetails.getUsername());
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return createToken(claims, userDetails.getUsername());
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(jwtSecretKey).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
