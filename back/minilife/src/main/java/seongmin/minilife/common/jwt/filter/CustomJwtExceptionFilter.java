package seongmin.minilife.common.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import seongmin.minilife.common.jwt.JwtExceptionUtil;
import seongmin.minilife.common.response.AuthErrorResponse;
import seongmin.minilife.common.response.exception.AuthErrorException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            AuthErrorException authErrorException = JwtExceptionUtil.determineAuthErrorException(e);

            sendAuthError(response, authErrorException);
        }
    }

    private void sendAuthError(HttpServletResponse response, AuthErrorException authErrorException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(authErrorException.getErrorCode().getHttpStatus().value());

        AuthErrorResponse authErrorResponse = new AuthErrorResponse(authErrorException.getErrorCode().name(), authErrorException.getMessage());
        objectMapper.writeValue(response.getWriter(), authErrorResponse);
    }
}
