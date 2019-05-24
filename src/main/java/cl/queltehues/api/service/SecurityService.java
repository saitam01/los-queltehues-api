package cl.queltehues.api.service;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    public Collection<Object> validate(String token) throws DriveException {
        User user = tokenProvider.getUser(token);
        List userList = userProvider.getUsers();
        if(userList.contains(user)){
            log.info("USER Exist!");
            return Collections.singletonList("OK");
        } else {
            log.info("USER NOT Exist!");
            return Collections.singletonList("User not exist");
        }

    }
}
