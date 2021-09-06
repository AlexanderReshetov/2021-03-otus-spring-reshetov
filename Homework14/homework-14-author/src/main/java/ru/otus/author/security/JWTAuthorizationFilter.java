package ru.otus.author.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.otus.author.domain.Role;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.otus.author.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            final DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
            final String userName = decodedJWT.getSubject();
            final String authorities = decodedJWT.getClaim(AUTHORITY_CLAIM).asString();

            if (userName != null) {
                return new UsernamePasswordAuthenticationToken(userName, null, Arrays.stream(authorities.split(",")).map(Role:: new).collect(Collectors.toList()));
            }
            return null;
        }
        return null;
    }
}
