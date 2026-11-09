package com.api.kwanza_services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "app.dev-user.enabled=true",
    "app.dev-user.email=admin@test.local",
    "app.dev-user.password=admin123",
    "app.dev-user.phone=+244900000001"
})
class AuthLoginSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginPorEmailDevolveTokenEAccedeAoMe() throws Exception {
        String token = login("admin@test.local", "admin123");

        mockMvc.perform(
                get("/api/auth/me")
                    .header("Authorization", "Bearer " + token)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").isNotEmpty())
            .andExpect(jsonPath("$.perfil").value("ADMIN"));
    }

    @Test
    void loginPorTelefoneDevolveToken() throws Exception {
        String token = login("+244900000001", "admin123");
        assertNotNull(token);
    }

    @Test
    void passwordErradaDevolve401() throws Exception {
        mockMvc.perform(
                post("/api/public/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "login", "admin@test.local",
                        "password", "password-errada"
                    )))
            )
            .andExpect(status().isUnauthorized());
    }

    @Test
    void endpointProtegidoSemTokenDevolve401() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void rotaSobApiPublicNaoExigeAutenticacao() throws Exception {
        // Não existe handler, mas a segurança deixa passar: 404, não 401.
        mockMvc.perform(get("/api/public/rota-inexistente"))
            .andExpect(status().isNotFound());
    }

    @Test
    void rotaForaDeApiPublicExigeAutenticacao() throws Exception {
        mockMvc.perform(get("/api/rota-inexistente"))
            .andExpect(status().isUnauthorized());
    }

    private String login(String login, String password) throws Exception {
        MvcResult result = mockMvc.perform(
                post("/api/public/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "login", login,
                        "password", password
                    )))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.tokenType").value("Bearer"))
            .andReturn();

        return objectMapper
            .readTree(result.getResponse().getContentAsString())
            .get("accessToken")
            .asText();
    }
}
