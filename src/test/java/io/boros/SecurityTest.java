package io.boros;

import io.boros.flightright.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturn401() throws Exception {
        mockMvc.perform(get("http://localhost:8080/members"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn200_CorrectAuth() throws Exception {
        String auth = Base64.getEncoder().encodeToString("admin:password".getBytes());
        mockMvc.perform(get("http://localhost:8080/members")
                .header("Authorization", "Basic " + auth))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldReturn200_IncorrectCredentials() throws Exception {
        String auth = Base64.getEncoder().encodeToString("admin:incorrect".getBytes());
        mockMvc.perform(get("http://localhost:8080/members")
                .header("Authorization", "Basic " + auth))
                .andExpect(status().isUnauthorized());
    }

}
