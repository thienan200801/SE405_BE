package uit.se122.ieltstinder.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uit.se122.ieltstinder.security.jwt.CheckJwtResult;
import uit.se122.ieltstinder.security.jwt.ExtractJwtResult;
import uit.se122.ieltstinder.security.jwt.JwtProvider;
import uit.se122.ieltstinder.service.UserService;
import uit.se122.ieltstinder.service.UserSessionService;

import java.io.IOException;
import java.util.Set;

import static uit.se122.ieltstinder.constant.HeaderConstant.*;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserSessionService sessionService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String accessToken = retrieveToken(request);

        if (StringUtils.isNotBlank(accessToken)) {
            ExtractJwtResult extractJwtResult = jwtProvider.extractClaims(accessToken);
            if (extractJwtResult.isValid() && sessionService.checkUserSession(extractJwtResult.getTokenId())) {
                Set<String> author = extractJwtResult.getAuthorities();
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        extractJwtResult.getUserId(),
                        extractJwtResult.getTokenId(),
                        author.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if (CheckJwtResult.EXPIRED == extractJwtResult.status()) {
                    response.setHeader(IS_TOKEN_EXPIRED_HEADER, "true");
                }
                setAnonymousContext();
            }
        } else {
            setAnonymousContext();
        }

        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        final String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(token) || !token.startsWith(TOKEN_PREFIX)) {
            return StringUtils.EMPTY;
        }
        return token.replace(TOKEN_PREFIX, StringUtils.EMPTY).trim();
    }

    private void setAnonymousContext() {
        SecurityContextHolder.clearContext();
//        SecurityContextHolder.getContext().setAuthentication(SecurityUtils.createAnonymous());
    }
}
