package cl.queltehues.api.controller;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.service.SecurityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

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
    public Collection authenticateUser(@RequestParam(value = "jwt") String token) throws DriveException {
        return securityService.validateUser(token);
    }
}
