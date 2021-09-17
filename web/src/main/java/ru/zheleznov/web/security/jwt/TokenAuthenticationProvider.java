package ru.zheleznov.web.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.zheleznov.impl.models.User;
import ru.zheleznov.impl.utils.JwtUtils;
import ru.zheleznov.web.security.details.UserDetailsImpl;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("UserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication;
        User user = jwtUtils.getUserFromJwtToken(authentication.getName());
        UserDetails userDetails = new UserDetailsImpl(user);
        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setUserDetails(userDetails);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
