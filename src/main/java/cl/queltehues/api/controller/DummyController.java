package cl.queltehues.api.controller;

import cl.queltehues.api.service.DummyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class DummyController {

    @Autowired
    private DummyService dummyService;

    @RequestMapping(value = "/helloworld",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Endpoint de prueba",
            notes = "Devuelve un JSON con 'Hello World' como respuesta",
            response = Map.class
    )
    public Map<String, String> getHelloWorld() {
        return Collections.singletonMap("response", dummyService.getDummyMessage());
    }
}
