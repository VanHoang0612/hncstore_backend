package com.hoang.hncstore_backend.core.security.jwt;

import com.hoang.hncstore_backend.auth.enums.ErrorCode;
import com.hoang.hncstore_backend.auth.enums.TokenType;
import com.hoang.hncstore_backend.core.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.expiration-time.access}")
    private long accessExpirationMs;

    @Value("${security.jwt.expiration-time.refresh}")
    private long refreshExpirationMs;


    //    trich xuat cac claim tu token tra ve kieu T
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //    trich xuat tat ca claims tu token
    public Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("Token đã hết hạn: {}", e.getMessage());
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED, null);
        } catch (Exception e) {
            log.error("Lỗi khi trích xuất claims từ token: {}", e.getMessage());
            throw new BusinessException(ErrorCode.UNAUTHORIZED, null);
        }

    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }

    public UUID extractJti(String token) {
        return extractClaim(token, claims -> UUID.fromString(claims.get("jti", String.class)));
    }

    public String generateAccessToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails, TokenType.ACCESS, accessExpirationMs);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails, TokenType.REFRESH, refreshExpirationMs);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            TokenType tokenType,
            long expirationMs
    ) {
        extraClaims.put("type", tokenType.name());
        long now = System.currentTimeMillis();
        if (tokenType == TokenType.REFRESH) {
            extraClaims.put("jti", UUID.randomUUID()
                    .toString());
        }
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMs))
                .signWith(getSingInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractClaim(token, Claims::getSubject);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getAccessTokenExpiry() {
        return accessExpirationMs;
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        try {
            String tokenType = extractTokenType(refreshToken);
//            Optional<RevokedToken> revokedToken = revokedTokenService.findByJti(extractJti(refreshToken));
            if (!TokenType.REFRESH.name()
                    .equals(tokenType)) {
                log.warn("Invalid token: Incorrect token type");
                return false;
            }
            if (isTokenExpired(refreshToken)) {
                log.warn("The token has expired");
                return false;
            }
//            if (revokedToken.isPresent()) {
//                log.warn("The token has been revoked: jti={}", revokedToken.get()
//                        .getJti());
//                return false;
//            }
            return true;


        } catch (Exception e) {
            log.warn("Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }

    public boolean isAccessTokenValid(String accessToken, UserDetails userDetails) {
        try {
            String tokenType = extractTokenType(accessToken);
            return TokenType.ACCESS.name()
                    .equals(tokenType) && isTokenValid(accessToken, userDetails);
        } catch (Exception e) {
            log.warn("Access token invalid: {}", e.getMessage());
            return false;
        }
    }
}
