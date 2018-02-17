package com.onlinegazeta.demo.configuration;

import com.onlinegazeta.demo.model.UserSession;
import com.onlinegazeta.demo.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.stream.Collectors;


public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        if(header!=null) {

            UserSession userSession = userSessionRepository.getBySessinId(header);
            if (userSession == null) {
                throw new UsernameNotFoundException("Unauthorized"); //401
            }


            UserDetails userDetails = new User(
                    userSession.getUser().getUsername(),
                    userSession.getUser().getPassword(),
                    true, true, true, true,
                    userSession.getUser().getRoles()
                            .stream().map(x -> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList()));

            UsernamePasswordAuthenticationToken securityUserToken = new UsernamePasswordAuthenticationToken(
                    userDetails, (Object) null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(securityUserToken);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

/*

package com.onlinegazeta.demo.configuration;

        import com.onlinegazeta.demo.model.UserSession;
        import com.onlinegazeta.demo.repository.UserSessionRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.web.filter.OncePerRequestFilter;

        import javax.servlet.FilterChain;
        import javax.servlet.ServletException;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
        import java.security.Principal;
        import java.util.Collections;
        import java.util.stream.Collectors;


public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        if(header==null){
            filterChain.doFilter(httpServletRequest,httpServletResponse); //403
            return;
        }
        UserSession userSession = userSessionRepository.getBySessinId(header);
        if(userSession==null){
            throw new UsernameNotFoundException("No such user"); //401
        }
        UserDetails userDetails = new User(
                userSession.getUser().getUsername(),
                userSession.getUser().getPassword(),
                true,true,true,true,
                userSession.getUser().getRoles().stream().
                        map(x->new SimpleGrantedAuthority(x)).
                        collect(Collectors.toList()));
        UsernamePasswordAuthenticationToken securityUserToken = new UsernamePasswordAuthenticationToken(
                userDetails,(Object) null, userDetails.getAuthorities());
        //securityUserToken.setDetails();
        SecurityContextHolder.getContext().setAuthentication(securityUserToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        return;
    }
}
*/
