package cl.queltehues.controller;

import cl.queltehues.aspect.LoggingInfo;
import cl.queltehues.service.DocService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
public class DocController {

    @Autowired
    private DocService docService;

    @RequestMapping(value = "/docs/boletas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Endpoint de documentos",
            notes = "Devuelve un JSON documentos como respuesta",
            response = Map.class
    )
    @LoggingInfo
    public Map<String, Collection> getboletas() {
        return Collections.singletonMap("response", docService.getBoletas());
    }
}
