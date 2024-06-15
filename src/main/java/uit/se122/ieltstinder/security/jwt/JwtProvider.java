package uit.se122.ieltstinder.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.config.ApplicationProperties;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.security.SecurityUtils;

import java.security.Key;
import java.util.*;

import static uit.se122.ieltstinder.constant.AppConstant.EMPTY_STR;

@Slf4j
@Component
public class JwtProvider {

    private final Key signingKey;
    private final long accessTokenInMinutes;
    private final long refreshTokenInHours;

    public JwtProvider(ApplicationProperties properties) {
        this.signingKey = getSignInKey(properties.getSecurity().jwt().secret());
        this.accessTokenInMinutes = properties.getSecurity().jwt().accessTokenInMinutes();
        this.refreshTokenInHours = properties.getSecurity().jwt().refreshTokenInHours();
    }

    public String generateVideoSdkToken(User user) {
        Map<String, Object> payload = new HashMap<>();
        String VIDEO_SDK_API_KEY = "82b5a153-4891-48f9-b26d-ea5a69df12ac";
        String VIDEO_SDK_SECRET_KEY = "de4d87a8a134a868691874a94f5c3ea47af5cea1eb542507cb84e7c581eef2af";

        payload.put("apikey", VIDEO_SDK_API_KEY);
        payload.put("permissions", new String[] { "allow_join" });
        payload.put("version", 2);
        payload.put("participantId", user.getId());

        return Jwts
                .builder()
                .setClaims(payload)
                .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                .signWith(SignatureAlgorithm.HS256, VIDEO_SDK_SECRET_KEY.getBytes())
                .compact();
    }

    public GenerateJwtResult generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public GenerateJwtResult generateToken(Map<String, Object> extraClaims, User user) {
        String refreshTokenId = SecurityUtils.generateRandomCode();
        String accessTokenId = SecurityUtils.generateRandomCode();
        Date issueAt = new Date(System.currentTimeMillis());

        Set<String> authorities = Set.of(user.getRole().toString());

        String refreshToken = Jwts.builder()
                .setId(refreshTokenId)
                .claim("userId", user.getId())
                .setSubject(user.getEmail())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .setIssuedAt(issueAt)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * refreshTokenInHours))
                .compact();

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * accessTokenInMinutes);

        String accessToken = Jwts.builder()
                .setClaims(extraClaims)
                .setId(accessTokenId)
                .claim("userId", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setSubject(user.getEmail())
                .claim("refreshId", refreshTokenId)
                .claim("authorities", authorities)
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        return new GenerateJwtResult(
                accessTokenId,
                accessToken,
                refreshToken,
                expiration.toInstant()
        );
    }

    public ExtractJwtResult extractClaims(String token) {
        try {
            return new ExtractJwtResult(CheckJwtResult.VALID, extractAllClaims(token));
        } catch (ExpiredJwtException exception) {
            return new ExtractJwtResult(CheckJwtResult.EXPIRED, exception.getClaims());
        } catch (Exception exception) {
            return new ExtractJwtResult(CheckJwtResult.INVALID, null);
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey(String secret) {
        final byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
