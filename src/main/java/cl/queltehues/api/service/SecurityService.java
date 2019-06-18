package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public Collection validateUser(String token) throws DriveException {
        User user = tokenProvider.getUser(token);
        return validate(user) ? Collections.singletonList("OK") : Collections.singletonList("User not exist.");
    }

    public String authorize(String user, String password, Optional<Boolean> rememberMe) throws DriveException {
        Authentication authentication;

        if(validate(tokenProvider.getUser(user, password))) {
            if (rememberMe.isPresent()) {
                authentication = new RememberMeAuthenticationToken(user, password, null);
            } else {
                authentication = new UsernamePasswordAuthenticationToken(user, password);
            }
            return tokenProvider.createToken(authentication, rememberMe.isPresent());
        } else {
            log.info("Authorization refuse");
            throw new AuthorizationServiceException("Autorizacion denegada.");
        }

    }

    private Boolean validate(User user) throws DriveException {
        List userList = userProvider.getUsers();
        if(userList.contains(user)){
            log.info(String.format("User %s exist.", user.getUsername()));
            return true;
        } else {
            log.info(String.format("User %s not exist.", user.getUsername()));
            return false;
        }
    }
}
