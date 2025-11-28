package com.hanif.portfolioapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.model.User;
import com.hanif.portfolioapi.repository.UserRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String username = jwtTokenUtil.extractUsername(token);

                if (username != null && jwtTokenUtil.isTokenValid(token)) {
                    User user = userRepository.findByUsername(username).orElse(null);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, List.of());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (MalformedJwtException e) {
                writeErrorResponse(ResponseMessages.INVALID_TOKEN, response);
                return;
            } catch (ExpiredJwtException e) {
                writeErrorResponse(ResponseMessages.EXPIRED_TOKEN, response);
                return;
            } catch (Exception e) {
                writeErrorResponse(ResponseMessages.AUTHENTICATION_FAIL, response);
                return;
            }


        }

        filterChain.doFilter(request, response);
    }

    public static void writeErrorResponse(String message, HttpServletResponse response) throws IOException {
        ApiResponse apiResponse = ApiResponse.builder()
                .success(false)
                .message(message)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

}
