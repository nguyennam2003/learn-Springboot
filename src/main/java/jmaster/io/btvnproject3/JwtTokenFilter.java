package jmaster.io.btvnproject3;

import jmaster.io.btvnproject3.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //doc token tu header
        String token = resolveToken(request);

            //verify token
            if (token != null) {
                //co token roi thi lay username, goi db len user
                String username = jwtTokenService.getUserName(token);
                if (username != null) {
                    Authentication auth = jwtTokenService.getAuthentication(username);
                    //set vao context de co dang nhap roi
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    //this is very important, since it guarantees the user is not authenticated
                    SecurityContextHolder.clearContext();
                    response.sendError(401, "No Auth");
                    return;
                }
            }
            filterChain.doFilter(request, response);
    }

    //Lay token tu request gui len: header, params, form
    public String resolveToken(HttpServletRequest req) {
        //check postman header
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
