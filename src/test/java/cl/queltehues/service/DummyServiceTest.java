package cl.queltehues.service;

import org.junit.Assert;
import org.junit.Test;

public class DummyServiceTest {

    private DummyService dummyService;

    @Test
    public void shouldTestDummyMethod() {
        dummyService = new DummyService();
        String text = dummyService.getDummyMessage();
        Assert.assertNotNull(text);
    }
}