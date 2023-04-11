package jmaster.io.btvnproject3.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("!!!");
        System.out.println(request.getServletPath());
        System.out.println(request.getMethod());

        String path = request.getServletPath();

        if (path.equals("/user/new")) { // dung thu vien java regex
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
                List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                if (!roles.contains("USER_ADMIN")) throw new AccessDeniedException("");

                return true;
            }
            throw new AccessDeniedException("");
        }
        return true;
    }
}
