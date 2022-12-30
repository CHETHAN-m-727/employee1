package com.firstock.employee.jwt.api.filter;


import com.firstock.employee.jwt.api.service.CustomUserDetailsService;
import com.firstock.employee.jwt.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userName = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
                System.out.println("user name is ---> " + userName);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = service.loadUserByUsername(userName);

                if (jwtUtil.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            System.out.println(e.toString() +"  Error In JWTFilter");
        }
    }
}