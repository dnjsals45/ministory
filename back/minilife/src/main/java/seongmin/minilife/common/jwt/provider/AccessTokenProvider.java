package seongmin.minilife.common.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import seongmin.minilife.common.jwt.dto.JwtTokenInfo;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenProvider implements TokenProvider {

    private final SecretKey signature;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final Duration tokenExpiration;

    public AccessTokenProvider(String accessSecret, Duration tokenExpiration) {
        final byte[] secretKeyBytes = Base64.getDecoder().decode(accessSecret);
        this.signature = Keys.hmacShaKeyFor(secretKeyBytes);
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public String generateToken(JwtTokenInfo tokenInfo) {
        final Date now = new Date();

        return Jwts.builder()
                .header()
                .add(createHeader())
                .and()
                .claims(createClaims("MiniLife-access", tokenInfo))
                .expiration(createExpiration(now, tokenExpiration.toMillis()))
                .issuedAt(now)
                .signWith(signature)
                .compact();
    }

    @Override
    public Map<String, Object> createHeader() {
        Map<String, Object> headers = new HashMap<>();

        headers.put("typ", "JWT");
        headers.put("alg", signatureAlgorithm.getValue());
        headers.put("regDate", System.currentTimeMillis());

        return headers;
    }

    @Override
    public Date createExpiration(final Date now, long duration) {
        return new Date(now.getTime() + duration);
    }

    @Override
    public Map<String, Object> createClaims(String subject, JwtTokenInfo tokenInfo) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("iss", "lost42");
        claims.put("sub", subject);
        claims.put("id", tokenInfo.getUserId());
        claims.put("role", tokenInfo.getRole());
        claims.put(tokenInfo.getOauthProvider() + "Id", tokenInfo.getOauthId());

        return claims;
    }

    @Override
    public Claims extractClaim(String token) {
        return Jwts.parser().verifyWith(signature).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public Boolean isTokenExpire(String token) {
        Date expiration = extractClaim(token).getExpiration();

        return expiration.before(new Date());
    }

    @Override
    public Long getIdFromToken(String token) {
        return extractClaim(token).get("id", Long.class);
    }


}
