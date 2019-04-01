package cl.queltehues.api.service;

import cl.queltehues.api.aspect.LoggingInfo;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

    @LoggingInfo
    public String getDummyMessage() {
        return "Hello World";
    }
}
