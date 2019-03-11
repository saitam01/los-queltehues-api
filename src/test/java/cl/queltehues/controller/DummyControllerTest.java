package cl.queltehues.controller;

import cl.queltehues.service.DummyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DummyControllerTest {

    @MockBean
    private DummyService dummyServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldTestHelloWorldUri() throws Exception {
        String dummyMessage = "DummyMessage";
        when(dummyServiceMock.getDummyMessage()).thenReturn(dummyMessage);

        mockMvc.perform(MockMvcRequestBuilders.get("/helloworld").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is(dummyMessage)));
    }
}
