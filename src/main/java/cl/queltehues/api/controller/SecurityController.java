package cl.queltehues.api.controller;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.service.SecurityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    public SecurityController() {}

    @GetMapping(value = "/auth",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Security endpoint",
            notes = "Validate user and password",
            response = Map.class
    )
    public Map<String, Collection> get(@RequestParam(value = "jwt") String token)
            throws DriveException {
        return Collections.singletonMap("response", securityService.validate(token));
    }
}