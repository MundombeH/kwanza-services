package com.api.kwanza_services;

import com.api.kwanza_services.Model.Localizacao.BairroModel;
import com.api.kwanza_services.Model.Localizacao.MunicipioModel;
import com.api.kwanza_services.Model.Localizacao.PaisModel;
import com.api.kwanza_services.Model.Localizacao.ProvinciasModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Repository.Localizacao.BairrosRepository;
import com.api.kwanza_services.Repository.Localizacao.MunicipiosRepository;
import com.api.kwanza_services.Repository.Localizacao.PaisesRepository;
import com.api.kwanza_services.Repository.Localizacao.ProvinciasRepository;
import com.api.kwanza_services.Repository.User.DefaultUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
class ControllersSmokeTest {

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

    @Autowired
    private DefaultUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UUID bairroId;
    private String adminToken;
    private UUID adminId;

    @BeforeEach
    void setUp() throws Exception {
        bairroId = criarBairro();
        adminToken = login("admin@test.local", "admin123");
        adminId = UUID.fromString(
            ler(mockMvc.perform(
                    get("/api/auth/me").header("Authorization", "Bearer " + adminToken)
                )
                .andExpect(status().isOk())
                .andReturn())
                .get("userId")
                .asText()
        );
    }

    @Test
    void adminListaEBuscaUtilizadoresMasClienteNaoPode() throws Exception {
        ContaCliente cliente = criarCliente();

        mockMvc.perform(
                get("/api/users")
                    .header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());

        mockMvc.perform(
                get("/api/users/" + cliente.id())
                    .header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(cliente.email()))
            .andExpect(jsonPath("$.perfilAtivo").value("CLIENTE"));

        mockMvc.perform(
                get("/api/users")
                    .header("Authorization", "Bearer " + cliente.token())
            )
            .andExpect(status().isForbidden());
    }

    @Test
    void clienteActualizaOsPropriosDadosMasNaoOsDeOutro() throws Exception {
        ContaCliente cliente = criarCliente();
        String novoEmail = "actualizado-" + UUID.randomUUID() + "@test.local";
        String novoTelefone = telefoneAleatorio();

        mockMvc.perform(
                put("/api/users/" + cliente.id())
                    .header("Authorization", "Bearer " + cliente.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Cliente Actualizado",
                        "email", novoEmail,
                        "numeroDeTelefone", novoTelefone
                    )))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Cliente Actualizado"))
            .andExpect(jsonPath("$.email").value(novoEmail));

        mockMvc.perform(
                put("/api/users/" + adminId)
                    .header("Authorization", "Bearer " + cliente.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "nome", "Intruso",
                        "email", "intruso-" + UUID.randomUUID() + "@test.local",
                        "numeroDeTelefone", telefoneAleatorio()
                    )))
            )
            .andExpect(status().isForbidden());
    }

    @Test
    void alteracaoDePassword() throws Exception {
        ContaCliente cliente = criarCliente();

        mockMvc.perform(
                patch("/api/users/me/password")
                    .header("Authorization", "Bearer " + cliente.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "currentPassword", "cliente123",
                        "newPassword", "cliente456",
                        "confirmPassword", "cliente456"
                    )))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Palavra-passe atualizada com sucesso"));

        login(cliente.telefone(), "cliente456");

        mockMvc.perform(
                patch("/api/users/me/password")
                    .header("Authorization", "Bearer " + cliente.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "currentPassword", "password-errada",
                        "newPassword", "cliente789",
                        "confirmPassword", "cliente789"
                    )))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void adminDesactivaUtilizador() throws Exception {
        ContaCliente cliente = criarCliente();

        mockMvc.perform(
                delete("/api/users/" + cliente.id())
                    .header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isNoContent());

        mockMvc.perform(
                get("/api/users/" + cliente.id())
                    .header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isNotFound());

        mockMvc.perform(
                post("/api/public/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "login", cliente.email(),
                        "password", "cliente123"
                    )))
            )
            .andExpect(status().isUnauthorized());
    }

    @Test
    void pesquisaDeFornecedores() throws Exception {
        mockMvc.perform(
                get("/api/fornecedores")
                    .header("Authorization", "Bearer " + adminToken)
                    .param("q", "teste")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void gestaoDeRoles() throws Exception {
        ContaCliente cliente = criarCliente();
        String url = "/api/users/" + cliente.id() + "/roles/CLIENTE";

        mockMvc.perform(
                get(url).header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.atribuida").value(true));

        mockMvc.perform(
                delete(url).header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isNoContent());

        mockMvc.perform(
                get(url).header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.atribuida").value(false));

        mockMvc.perform(
                post(url).header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isNoContent());

        mockMvc.perform(
                get(url).header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.atribuida").value(true));

        mockMvc.perform(
                get(url).header("Authorization", "Bearer " + cliente.token())
            )
            .andExpect(status().isForbidden());
    }

    @Test
    void trocaDePerfil() throws Exception {
        ContaCliente cliente = criarCliente();
        String url = "/api/perfis/usuarios/" + cliente.id() + "/ativo/";

        mockMvc.perform(
                put(url + "CLIENTE")
                    .header("Authorization", "Bearer " + cliente.token())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.perfilAtivo").value("CLIENTE"));

        // O cliente não tem ROLE_FORNECEDOR, por isso a troca é recusada.
        mockMvc.perform(
                put(url + "FORNECEDOR")
                    .header("Authorization", "Bearer " + cliente.token())
            )
            .andExpect(status().isBadRequest());

        mockMvc.perform(
                get("/api/perfis/usuarios/" + cliente.id() + "/CLIENTE")
                    .header("Authorization", "Bearer " + adminToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.existe").value(true));
    }

    @Test
    void adicionarPerfisAUtilizadorExistente() throws Exception {
        UUID semPerfilCliente = criarUtilizadorSemPerfil();
        UUID semPerfilAdmin = criarUtilizadorSemPerfil();

        mockMvc.perform(
                post("/api/perfis/clientes")
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "usuario", semPerfilCliente.toString(),
                        "sexo", "FEMININO",
                        "bairro", bairroId.toString()
                    )))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.perfilAtivo").value("CLIENTE"))
            .andExpect(jsonPath("$.roles[0].descricao").value("ROLE_CLIENTE"));

        mockMvc.perform(
                post("/api/perfis/admins")
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                        "usuario", semPerfilAdmin.toString()
                    )))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.perfilAtivo").value("ADMIN"))
            .andExpect(jsonPath("$.roles[0].descricao").value("ROLE_ADMIN"));
    }

    private record ContaCliente(UUID id, String email, String telefone, String token) {
    }

    private ContaCliente criarCliente() throws Exception {
        String email = "cliente-" + UUID.randomUUID() + "@test.local";
        String telefone = telefoneAleatorio();

        MvcResult result = mockMvc.perform(
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
            .andReturn();

        UUID id = UUID.fromString(ler(result).get("id").asText());

        return new ContaCliente(id, email, telefone, login(telefone, "cliente123"));
    }

    private UUID criarUtilizadorSemPerfil() {
        DefaultUserModel user = new DefaultUserModel();
        user.setNome("Utilizador Sem Perfil");
        user.setEmail("sem-perfil-" + UUID.randomUUID() + "@test.local");
        user.setNumeroDeTelefone(telefoneCompletoAleatorio());
        user.setPassword(passwordEncoder.encode("semperfil123"));
        user.setIsActive(true);

        return userRepository.save(user).getId();
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

        return ler(result).get("accessToken").asText();
    }

    private tools.jackson.databind.JsonNode ler(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private UUID criarBairro() {
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

        return bairrosRepository.save(bairro).getId();
    }

    /**
     * Telefone no formato local (9XXXXXXXX), como o utilizador o introduz.
     */
    private String telefoneAleatorio() {
        return "9" + String.format("%08d", (int) (Math.random() * 100_000_000));
    }

    /**
     * Telefone já normalizado, para gravar directamente na entidade.
     */
    private String telefoneCompletoAleatorio() {
        return "+244" + telefoneAleatorio();
    }
}
