package com.api.kwanza_services;

import com.api.kwanza_services.Model.Localizacao.BairroModel;
import com.api.kwanza_services.Model.Localizacao.MunicipioModel;
import com.api.kwanza_services.Model.Localizacao.PaisModel;
import com.api.kwanza_services.Model.Localizacao.ProvinciasModel;
import com.api.kwanza_services.Repository.Localizacao.BairrosRepository;
import com.api.kwanza_services.Repository.Localizacao.MunicipiosRepository;
import com.api.kwanza_services.Repository.Localizacao.PaisesRepository;
import com.api.kwanza_services.Repository.Localizacao.ProvinciasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistoClienteSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaisesRepository paisesRepository;

    @Autowired
    private ProvinciasRepository provinciasRepository;

    @Autowired
    private MunicipiosRepository municipiosRepository;

    @Autowired
    private BairrosRepository bairrosRepository;

    private UUID bairroId;

    @BeforeEach
    void criarBairro() {
        PaisModel pais = new PaisModel();
        pais.setDescricao("Pais-" + UUID.randomUUID());
        pais = paisesRepository.save(pais);

        ProvinciasModel provincia = new ProvinciasModel();
        provincia.setDescricao("Provincia-" + UUID.randomUUID());
        provincia.setPais(pais);
        provincia = provinciasRepository.save(provincia);

        MunicipioModel municipio = new MunicipioModel();
        municipio.setDescricao("Municipio-" + UUID.randomUUID());
        municipio.setProvincia(provincia);
        municipio = municipiosRepository.save(municipio);

        BairroModel bairro = new BairroModel();
        bairro.setDescricao("Bairro-" + UUID.randomUUID());
        bairro.setMunicipio(municipio);
        bairro = bairrosRepository.save(bairro);

        bairroId = bairro.getId();
    }

    @Test
    void registoDeClienteCriaContaPerfilERoleEPermiteLogin() throws Exception {
        String email = "cliente-" + UUID.randomUUID() + "@test.local";
        String telefone = telefoneAleatorio();

        mockMvc.perform(
                post("/api/public/auth/register/cliente")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Cliente de Teste",
                        "email", email,
                        "password", "cliente123",
                        "numeroDeTelefone", telefone,
                        "sexo", "MASCULINO",
                        "bairroId", bairroId.toString()
                    )))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.numeroDeTelefone").value("+244" + telefone))
            .andExpect(jsonPath("$.perfilAtivo").value("CLIENTE"))
            .andExpect(jsonPath("$.roles[0].descricao").value("ROLE_CLIENTE"));

        // O login funciona com o telefone no formato em que foi registado.
        String token = login(telefone, "cliente123");

        mockMvc.perform(
                get("/api/auth/me")
                    .header("Authorization", "Bearer " + token)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.perfil").value("CLIENTE"))
            .andExpect(jsonPath("$.roles[0]").value("ROLE_CLIENTE"));
    }

    @Test
    void loginFuncionaComEmailEmMaiusculas() throws Exception {
        String email = "cliente-" + UUID.randomUUID() + "@test.local";

        registar(email, telefoneAleatorio(), "cliente123");

        String token = login(email.toUpperCase(), "cliente123");

        mockMvc.perform(
                get("/api/auth/me")
                    .header("Authorization", "Bearer " + token)
            )
            .andExpect(status().isOk());
    }

    @Test
    void emailDuplicadoDevolve409() throws Exception {
        String email = "cliente-" + UUID.randomUUID() + "@test.local";

        registar(email, telefoneAleatorio(), "cliente123");

        mockMvc.perform(
                post("/api/public/auth/register/cliente")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Outro Cliente",
                        "email", email,
                        "password", "cliente123",
                        "numeroDeTelefone", telefoneAleatorio(),
                        "sexo", "FEMININO",
                        "bairroId", bairroId.toString()
                    )))
            )
            .andExpect(status().isConflict());
    }

    @Test
    void bairroInexistenteDevolve404() throws Exception {
        mockMvc.perform(
                post("/api/public/auth/register/cliente")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Cliente de Teste",
                        "email", "cliente-" + UUID.randomUUID() + "@test.local",
                        "password", "cliente123",
                        "numeroDeTelefone", telefoneAleatorio(),
                        "sexo", "MASCULINO",
                        "bairroId", UUID.randomUUID().toString()
                    )))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void telefoneInvalidoDevolve400() throws Exception {
        mockMvc.perform(
                post("/api/public/auth/register/cliente")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Cliente de Teste",
                        "email", "cliente-" + UUID.randomUUID() + "@test.local",
                        "password", "cliente123",
                        "numeroDeTelefone", "123",
                        "sexo", "MASCULINO",
                        "bairroId", bairroId.toString()
                    )))
            )
            .andExpect(status().isBadRequest());
    }

    private void registar(String email, String telefone, String password) throws Exception {
        mockMvc.perform(
                post("/api/public/auth/register/cliente")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Cliente de Teste",
                        "email", email,
                        "password", password,
                        "numeroDeTelefone", telefone,
                        "sexo", "MASCULINO",
                        "bairroId", bairroId.toString()
                    )))
            )
            .andExpect(status().isCreated());
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
            .andReturn();

        return objectMapper
            .readTree(result.getResponse().getContentAsString())
            .get("accessToken")
            .asText();
    }

    /**
     * Gera um número angolano válido no formato local: 9XXXXXXXX.
     */
    private String telefoneAleatorio() {
        return "9" + String.format("%08d", (int) (Math.random() * 100_000_000));
    }
}
