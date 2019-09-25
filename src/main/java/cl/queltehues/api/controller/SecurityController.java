package cl.queltehues.api.controller;

import cl.queltehues.api.domain.Vecino;
import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.service.SecurityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SecurityController {

    private SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(value = "/token",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "get a valid token",
            notes = "retrieve a jwt token with user and password",
            response = String.class
    )
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = {"https://los-queltehues-web.herokuapp.com", "http://www.los-queltehues.cl"})
    public Map<String, String> authorize(@RequestBody Vecino vecino) throws DriveException {
        Map<String, String> response = new HashMap<>();
        response.put("token", securityService.authorize(vecino.getUsername(), vecino.getPassword(),
                Optional.ofNullable(vecino.getRememberMe())));
        return response;
    }
}
