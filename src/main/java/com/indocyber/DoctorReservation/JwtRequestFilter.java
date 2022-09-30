package com.indocyber.DoctorReservation;

import com.indocyber.DoctorReservation.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Get header called Authorization from a request
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authorizationHeader != null){

            //Replace text "Bearer" with empty string
            token = authorizationHeader.replace("Bearer", "");

            //Get username for JwtToken
            username = jwtToken.getUsername(token);
        }

        //container spring security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(username != null && authentication == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtToken.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //meneruskan ke filter yg lain
        filterChain.doFilter(request, response);
    }
}
