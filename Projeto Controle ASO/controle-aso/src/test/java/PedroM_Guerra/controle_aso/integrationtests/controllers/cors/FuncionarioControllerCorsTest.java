package PedroM_Guerra.controle_aso.integrationtests.controllers.cors;

import PedroM_Guerra.controle_aso.config.TestConfigs;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.integrationtests.dto.FuncionarioDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FuncionarioControllerCorsTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static FuncionarioDTO funcionario;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        funcionario = new FuncionarioDTO();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockFuncionario();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .setBasePath("/api/funcionario/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(funcionario)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        FuncionarioDTO createdFuncionario = objectMapper.readValue(content, FuncionarioDTO.class);
        funcionario = createdFuncionario;

        assertNotNull(createdFuncionario.getId());
        assertNotNull(createdFuncionario.getNome());
        assertNotNull(createdFuncionario.getCpf());
        assertNotNull(createdFuncionario.getMatricula());
        assertNotNull(createdFuncionario.getDataNascimento());
        assertNotNull(createdFuncionario.getGenero());
        assertNotNull(createdFuncionario.getSetor());
        assertNotNull(createdFuncionario.getCargo());
        assertNotNull(createdFuncionario.getDataAdmissao());
        assertNotNull(createdFuncionario.getDataDemissao());

        assertTrue(createdFuncionario.getId() > 0);

        assertEquals("Júlio da Silva", createdFuncionario.getNome());
        assertEquals("02334378518", createdFuncionario.getCpf());
        assertEquals("54323", createdFuncionario.getMatricula());
        assertEquals(LocalDate.of(1981, 2, 2), createdFuncionario.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, createdFuncionario.getGenero());
        assertEquals(SetorFuncionario.ADMINISTRACAO, createdFuncionario.getSetor());
        assertEquals(CargoFuncionario.SUPERVISOR, createdFuncionario.getCargo());
        assertEquals(LocalDate.of(2001, 5, 7), createdFuncionario.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 11, 2), createdFuncionario.getDataDemissao());
        assertTrue(createdFuncionario.getEnabled());
    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_NOT_WORK)
                .setBasePath("/api/funcionario/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(funcionario)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/funcionario/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", funcionario.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        FuncionarioDTO createdFuncionario = objectMapper.readValue(content, FuncionarioDTO.class);
        funcionario = createdFuncionario;

        assertNotNull(createdFuncionario.getId());
        assertNotNull(createdFuncionario.getNome());
        assertNotNull(createdFuncionario.getCpf());
        assertNotNull(createdFuncionario.getMatricula());
        assertNotNull(createdFuncionario.getDataNascimento());
        assertNotNull(createdFuncionario.getGenero());
        assertNotNull(createdFuncionario.getSetor());
        assertNotNull(createdFuncionario.getCargo());
        assertNotNull(createdFuncionario.getDataAdmissao());
        assertNotNull(createdFuncionario.getDataDemissao());

        assertTrue(createdFuncionario.getId() > 0);

        assertEquals("Júlio da Silva", createdFuncionario.getNome());
        assertEquals("02334378518", createdFuncionario.getCpf());
        assertEquals("54323", createdFuncionario.getMatricula());
        assertEquals(LocalDate.of(1981, 2, 2), createdFuncionario.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, createdFuncionario.getGenero());
        assertEquals(SetorFuncionario.ADMINISTRACAO, createdFuncionario.getSetor());
        assertEquals(CargoFuncionario.SUPERVISOR, createdFuncionario.getCargo());
        assertEquals(LocalDate.of(2001, 5, 7), createdFuncionario.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 11, 2), createdFuncionario.getDataDemissao());
        assertTrue(createdFuncionario.getEnabled());
    }

    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_NOT_WORK)
                .setBasePath("/api/funcionario/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", funcionario.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @AfterAll
    static void tearDown() {
        if (funcionario != null && funcionario.getId() != null) {
            specification = new RequestSpecBuilder()
                    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                    .setBasePath("/api/funcionario/v1")
                    .setPort(TestConfigs.SERVER_PORT)
                    .build();

            given(specification)
                    .pathParam("id", funcionario.getId())
                    .when()
                    .delete("{id}")
                    .then()
                    .statusCode(204);
        }
    }

    private void mockFuncionario() {
        funcionario.setNome("Júlio da Silva");
        funcionario.setCpf("02334378518");
        funcionario.setMatricula("54323");
        funcionario.setDataNascimento(LocalDate.of(1981, 2, 2));
        funcionario.setGenero(GeneroFuncionario.MASCULINO);
        funcionario.setSetor(SetorFuncionario.ADMINISTRACAO);
        funcionario.setCargo(CargoFuncionario.SUPERVISOR);
        funcionario.setDataAdmissao(LocalDate.of(2001, 5, 7));
        funcionario.setDataDemissao(LocalDate.of(2025, 11, 2));
        funcionario.setEnabled(true);
    }
}