package cl.queltehues.api.controller;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.service.DocService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
public class DocController {
    private DocService docService;

    @Autowired
    public DocController(DocService docService) {
        this.docService = docService;
    }

    @GetMapping(value = "/docs/{folderName}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Documents endpoint",
            notes = "Return a list of documents",
            response = Map.class
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Collection> get(@PathVariable(value = "folderName") String folderName)
            throws DriveException {
        return Collections.singletonMap("response", docService.getFiles(folderName));
    }
}
