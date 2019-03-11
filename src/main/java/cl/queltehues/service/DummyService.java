package cl.queltehues.service;

import cl.queltehues.aspect.LoggingInfo;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

	@LoggingInfo
    public String getDummyMessage() {
        return "Hello World";
    }
}
