package io.exsuslabs.AuthorizationServer;

import io.exsuslabs.AuthorizationServer.controller.UserController;
import io.exsuslabs.AuthorizationServer.service.AuthenticationService;
import io.exsuslabs.AuthorizationServer.service.UserManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserManagementRestControllerIntegrationTest {
    @MockBean
    UserManagementService userManagementService;
    @MockBean
    AuthenticationService authenticationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUser_CorrectInfo_StatusOK() {

    }

}
