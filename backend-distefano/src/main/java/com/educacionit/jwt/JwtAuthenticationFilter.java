package com.educacionit.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		
	    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
	    if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
	        String token = header.substring(7);
	        try {
	            String username = jwtService.extractUsername(token);
	            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	                if (jwtService.validateToken(token, userDetails)) {
	                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(authToken);
	                          
	                    HttpSession session = request.getSession();
                        session.setAttribute("token", token);
                        if (session != null) {
                            if (token != null) {
                                request = new HttpServletRequestWrapper(request) {
                                    @Override
                                    public String getHeader(String name) {
                                        if (HttpHeaders.AUTHORIZATION.equals(name)) {
                                            return "Bearer " + token;
                                        }
                                        return super.getHeader(name);
                                    }
                                };
                            }
                        }
	                }
	            }
	        } catch (Exception e) {
	            logger.error("Cannot set user authentication: {}", e);
	        }
	    }
	    filterChain.doFilter(request, response);
	}


	
	
	@SuppressWarnings("unused")
	private String getTokenFromRequest(HttpServletRequest request) {
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

}