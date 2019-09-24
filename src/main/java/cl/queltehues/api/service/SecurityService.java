package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecurityService {

    private TokenProvider tokenProvider;
    private UserProvider userProvider;

    @Autowired
    public SecurityService(TokenProvider tokenProvider, UserProvider userProvider) {
        this.tokenProvider = tokenProvider;
        this.userProvider = userProvider;
    }

    /*public Collection validateUser() throws DriveException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = tokenProvider.getUser(authentication.getName());
        return validate(user) ? Collections.singletonList("OK") : Collections.singletonList("User not exist.");
    }*/

    public String authorize(String user, String password, Optional<Boolean> rememberMe) throws DriveException {
        Authentication authentication;

        if(validate(tokenProvider.getUser(user, password))) {
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String [] {"ROLE_USER"}).map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            if (rememberMe.isPresent()) {
                authentication = new RememberMeAuthenticationToken(user, password, authorities);
            } else {
                authentication = new UsernamePasswordAuthenticationToken(user, password, authorities);
            }
            return tokenProvider.createToken(authentication, rememberMe.isPresent());
        } else {
            log.info("Authorization refuse");
            throw new AuthorizationServiceException("Autorizacion denegada.");
        }

    }

    private Boolean validate(User user) throws DriveException {
        List userList = userProvider.getUsers();
        if(!userList.isEmpty() && userList.contains(user)){
            log.info(String.format("User %s exist.", user.getUsername()));
            return true;
        } else {
            log.info(String.format("User %s not exist.", user.getUsername()));
            return false;
        }
    }
}
