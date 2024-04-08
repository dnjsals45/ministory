package seongmin.ministory.common.jwt.provider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import seongmin.ministory.common.jwt.dto.JwtTokenInfo;

import java.util.Date;
import java.util.Map;

@Component
public interface TokenProvider {

    /**
     * 요청으로부터 로부터 토큰을 추출하는 메서드
     */
    default String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * JwtTokenInfo 의 정보를 바탕으로 Token 을 발급
     */
    String generateToken(JwtTokenInfo tokenInfo);

//    /**
//     * token 에서 userId를 가져옮
//     */
//    Long getIdFromToken();

    /**
     * Token 의 Header 를 만듦
     */
    Map<String, Object> createHeader();

    /**
     * 토큰의 유효기간을 설정
     */
    Date createExpiration(final Date now, long duration);

    /**
     * 토큰 Claim 을 설정
     */
    Map<String, Object> createClaims(String subject, JwtTokenInfo tokenInfo);

    /**
     * token 에서 claim 추출
     */
    Claims extractClaim(String token);

    /**
     * 토큰의 만료기간이 지났는지 파악
     */
    Boolean isTokenExpire(String token);

    /**
     * 토큰에서 user id 를 추출하는 메서드
     */
    Long getIdFromToken(String token);
}
