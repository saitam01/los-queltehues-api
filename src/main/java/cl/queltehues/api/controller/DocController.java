package cl.queltehues.api.controller;

import cl.queltehues.api.aspect.LoggingInfo;
import cl.queltehues.api.service.DocService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
public class DocController {

    @Autowired
    private DocService docService;

    public DocController() {
    }

    @GetMapping(value = "/docs/boletas",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Endpoint de documentos",
            notes = "Devuelve un JSON documentos como respuesta",
            response = Map.class
    )
    @LoggingInfo
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Collection> getboletas() {
        return Collections.singletonMap("response", docService.getBoletas());
    }
}
