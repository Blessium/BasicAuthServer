package io.exsuslabs.AuthorizationServer;

import com.google.gson.Gson;
import io.exsuslabs.AuthorizationServer.controller.UserController;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;
import io.exsuslabs.AuthorizationServer.service.AuthenticationService;
import io.exsuslabs.AuthorizationServer.service.UserManagementService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementRestControllerIntegrationTest {
    @MockBean
    UserManagementService userManagementService;
    @MockBean
    AuthenticationService authenticationService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createUser_CorrectInfo_StatusCreated() throws Exception{
        UserDomain user = new UserDomain();
        user.setUsername("invented");
        user.setEmail("not@exist.com");
        user.setPassword("null");
        Gson gson = new Gson();
        String json_user = gson.toJson(user);
        this.mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(json_user)
                ).andExpect(status().isCreated())
                .andReturn();

    }

}
