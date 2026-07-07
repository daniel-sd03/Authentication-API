package sodresoftwares.login.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sodresoftwares.login.dto.AuthenticationDTO;
import sodresoftwares.login.dto.RegisterDTO;
import sodresoftwares.login.infra.security.SecurityFilter;
import sodresoftwares.login.model.user.UserRole;
import sodresoftwares.login.services.AuthenticationService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AuthenticationController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
@DisplayName("AuthenticationController Tests")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Object> jsonTester;

    @MockitoBean
    private AuthenticationService authService;

    private AuthenticationDTO authenticationDTO;
    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        authenticationDTO = new AuthenticationDTO("user@test.com", "password123");
        registerDTO = new RegisterDTO("user@test.com", "password123", UserRole.USER);
    }

    // ==================== LOGIN TESTS ====================

    @Test
    @DisplayName("Should login successfully and return token (HTTP 200)")
    void testLogin_Success() throws Exception {
        // Arrange
        String VALID_TOKEN = "jwt-token-example";
        when(authService.login(any(AuthenticationDTO.class))).thenReturn(VALID_TOKEN);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTester.write(authenticationDTO).getJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(VALID_TOKEN)));

        verify(authService).login(any(AuthenticationDTO.class));
    }

    @Test
    @DisplayName("Should return 400 when login or password are blank")
    void testLogin_ValidationErrors() throws Exception {
        // Arrange
        AuthenticationDTO invalidDTO = new AuthenticationDTO("", "");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTester.write(invalidDTO).getJson()))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(authService);
    }

    // ==================== REGISTER TESTS ====================

    @Test
    @DisplayName("Should register new user successfully (HTTP 201)")
    void testRegister_Success() throws Exception {
        // Arrange
        doNothing().when(authService).register(any(RegisterDTO.class));

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTester.write(registerDTO).getJson()))
                .andExpect(status().isCreated());

        verify(authService).register(any(RegisterDTO.class));
    }

    @Test
    @DisplayName("Should return 400 when register fields are blank")
    void testRegister_ValidationErrors() throws Exception {
        RegisterDTO invalidDTO = new RegisterDTO("", "", null);

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTester.write(invalidDTO).getJson()))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(authService);
    }
}