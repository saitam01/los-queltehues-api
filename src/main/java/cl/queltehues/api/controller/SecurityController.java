package cl.queltehues.api.controller;

import cl.queltehues.api.domain.Vecino;
import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.service.SecurityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class SecurityController {

    private SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping(value = "/auth",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Security endpoint",
            notes = "Validate user and password",
            response = Map.class
    )
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection authorizeUser(@RequestParam(value = "jwt") String token) throws DriveException {
        return securityService.validateUser(token);
    }

    @PostMapping(value = "/token",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "get a valid token",
            notes = "retrieve a jwt token with user and password",
            response = String.class
    )
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, String> authorize(@RequestBody Vecino vecino) throws DriveException {
        return Collections.singletonMap("token", securityService.authorize(vecino.getUsername(), vecino.getPassword(),
                Optional.ofNullable(vecino.getRememberMe())));
    }
}
