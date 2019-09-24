package cl.queltehues.api.controller;

import cl.queltehues.api.exception.DriveException;
import cl.queltehues.api.service.ContentService;
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
public class ContentInfoController {

    private ContentService contentService;

    @Autowired
    public ContentInfoController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping(value = "/content/gc/{house}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Content endpoint",
            notes = "Return a common expenses",
            response = Map.class
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Collection> getCommonExpenses(@PathVariable(value = "house") String houseNumber)
            throws DriveException {
        return Collections.singletonMap("response", contentService.getCommonExpenses(houseNumber));
    }

    @GetMapping(value = "/content/news",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Content endpoint",
            notes = "Return a news list",
            response = Map.class
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Collection> getNews() throws DriveException {
        return Collections.singletonMap("response", contentService.getNews());
    }

    @GetMapping(value = "/content/doorKeeper",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Content endpoint",
            notes = "Return a doorkeeper list",
            response = Map.class
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Collection> getDoorKeeper() throws DriveException {
        return Collections.singletonMap("response", contentService.getDoorKeeper());
    }

    @GetMapping(value = "/content/comite",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Content endpoint",
            notes = "Return comite list",
            response = Map.class
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Collection> getComite() throws DriveException {
        return Collections.singletonMap("response", contentService.getComite());
    }
}
