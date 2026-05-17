package PedroM_Guerra.controle_aso.integrationtests.controllers.withjson;

import PedroM_Guerra.controle_aso.config.TestConfigs;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.integrationtests.dto.FuncionarioDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FuncionarioControllerJsonTest {

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
    void createTest() throws JsonProcessingException {
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
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        FuncionarioDTO createdFuncionario = objectMapper.readValue(content, FuncionarioDTO.class);
        funcionario = createdFuncionario;

        assertNotNull(createdFuncionario.getId());
        assertTrue(createdFuncionario.getId() > 0);

        assertEquals("Marcielly Gislayne", createdFuncionario.getNome());
        assertEquals("12212312987", createdFuncionario.getCpf());
        assertEquals("54454", createdFuncionario.getMatricula());
        assertEquals(LocalDate.of(1999, 2, 2), createdFuncionario.getDataNascimento());
        assertEquals(GeneroFuncionario.FEMININO, createdFuncionario.getGenero());
        assertEquals(SetorFuncionario.TECNOLOGIA_INFORMACAO, createdFuncionario.getSetor());
        assertEquals(CargoFuncionario.TECNICO, createdFuncionario.getCargo());
        assertEquals(LocalDate.of(2001, 5, 7), createdFuncionario.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 11, 2), createdFuncionario.getDataDemissao());
        assertTrue(createdFuncionario.getEnabled());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        funcionario.setNome("Marciely Gislaine");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(funcionario)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        FuncionarioDTO createdFuncionario = objectMapper.readValue(content, FuncionarioDTO.class);
        funcionario = createdFuncionario;

        assertNotNull(createdFuncionario.getId());
        assertTrue(createdFuncionario.getId() > 0);

        assertEquals("Marciely Gislaine", createdFuncionario.getNome());
        assertEquals("12212312987", createdFuncionario.getCpf());
        assertEquals("54454", createdFuncionario.getMatricula());
        assertEquals(LocalDate.of(1999, 2, 2), createdFuncionario.getDataNascimento());
        assertEquals(GeneroFuncionario.FEMININO, createdFuncionario.getGenero());
        assertEquals(SetorFuncionario.TECNOLOGIA_INFORMACAO, createdFuncionario.getSetor());
        assertEquals(CargoFuncionario.TECNICO, createdFuncionario.getCargo());
        assertEquals(LocalDate.of(2001, 5, 7), createdFuncionario.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 11, 2), createdFuncionario.getDataDemissao());
        assertTrue(createdFuncionario.getEnabled());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", funcionario.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        FuncionarioDTO createdFuncionario = objectMapper.readValue(content, FuncionarioDTO.class);
        funcionario = createdFuncionario;

        assertNotNull(createdFuncionario.getId());
        assertTrue(createdFuncionario.getId() > 0);

        assertEquals("Marciely Gislaine", createdFuncionario.getNome());
        assertEquals("12212312987", createdFuncionario.getCpf());
        assertEquals("54454", createdFuncionario.getMatricula());
        assertEquals(LocalDate.of(1999, 2, 2), createdFuncionario.getDataNascimento());
        assertEquals(GeneroFuncionario.FEMININO, createdFuncionario.getGenero());
        assertEquals(SetorFuncionario.TECNOLOGIA_INFORMACAO, createdFuncionario.getSetor());
        assertEquals(CargoFuncionario.TECNICO, createdFuncionario.getCargo());
        assertEquals(LocalDate.of(2001, 5, 7), createdFuncionario.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 11, 2), createdFuncionario.getDataDemissao());
        assertTrue(createdFuncionario.getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", funcionario.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        FuncionarioDTO createdFuncionario = objectMapper.readValue(content, FuncionarioDTO.class);
        funcionario = createdFuncionario;

        assertNotNull(createdFuncionario.getId());
        assertTrue(createdFuncionario.getId() > 0);

        assertEquals("Marciely Gislaine", createdFuncionario.getNome());
        assertEquals("12212312987", createdFuncionario.getCpf());
        assertEquals("54454", createdFuncionario.getMatricula());
        assertEquals(LocalDate.of(1999, 2, 2), createdFuncionario.getDataNascimento());
        assertEquals(GeneroFuncionario.FEMININO, createdFuncionario.getGenero());
        assertEquals(SetorFuncionario.TECNOLOGIA_INFORMACAO, createdFuncionario.getSetor());
        assertEquals(CargoFuncionario.TECNICO, createdFuncionario.getCargo());
        assertEquals(LocalDate.of(2001, 5, 7), createdFuncionario.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 11, 2), createdFuncionario.getDataDemissao());
        assertFalse(createdFuncionario.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", funcionario.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<FuncionarioDTO> funcionarios = objectMapper.readValue(content, new TypeReference<List<FuncionarioDTO>>() {});

        FuncionarioDTO funcionarioOne = funcionarios.get(0);

        assertNotNull(funcionarioOne.getId());
        assertTrue(funcionarioOne.getId() > 0);

        assertEquals("Carlos Fagundes", funcionarioOne.getNome());
        assertEquals("1234561377", funcionarioOne.getCpf());
        assertEquals("15541", funcionarioOne.getMatricula());
        assertEquals(LocalDate.of(2002, 10, 6), funcionarioOne.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioOne.getGenero());
        assertEquals(SetorFuncionario.TECNOLOGIA_INFORMACAO, funcionarioOne.getSetor());
        assertEquals(CargoFuncionario.ESTAGIARIO, funcionarioOne.getCargo());
        assertEquals(LocalDate.of(2025, 3, 11), funcionarioOne.getDataAdmissao());
        assertNull(funcionarioOne.getDataDemissao());
        assertTrue(funcionarioOne.getEnabled());

        FuncionarioDTO funcionarioFour = funcionarios.get(4);

        assertNotNull(funcionarioFour.getId());
        assertTrue(funcionarioFour.getId() > 0);

        assertEquals("Carlos Eduardo", funcionarioFour.getNome());
        assertEquals("1234561381", funcionarioFour.getCpf());
        assertEquals("15545", funcionarioFour.getMatricula());
        assertEquals(LocalDate.of(1992, 11, 2), funcionarioFour.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioFour.getGenero());
        assertEquals(SetorFuncionario.ENGENHARIA, funcionarioFour.getSetor());
        assertEquals(CargoFuncionario.ENGENHEIRO, funcionarioFour.getCargo());
        assertEquals(LocalDate.of(2022, 6, 10), funcionarioFour.getDataAdmissao());
        assertEquals(LocalDate.of(2026, 4, 3), funcionarioFour.getDataDemissao());
        assertTrue(funcionarioFour.getEnabled());
    }

//    @AfterAll
//    static void tearDown() {
//        if (funcionario != null && funcionario.getId() != null) {
//            specification = new RequestSpecBuilder()
//                    .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
//                    .setBasePath("/api/funcionario/v1")
//                    .setPort(TestConfigs.SERVER_PORT)
//                    .build();
//
//            given(specification)
//                    .pathParam("id", funcionario.getId())
//                    .when()
//                    .delete("{id}")
//                    .then()
//                    .statusCode(204);
//        }
//    }

    private void mockFuncionario() {
        funcionario.setNome("Marcielly Gislayne");
        funcionario.setCpf("12212312987");
        funcionario.setMatricula("54454");
        funcionario.setDataNascimento(LocalDate.of(1999, 2, 2));
        funcionario.setGenero(GeneroFuncionario.FEMININO);
        funcionario.setSetor(SetorFuncionario.TECNOLOGIA_INFORMACAO);
        funcionario.setCargo(CargoFuncionario.TECNICO);
        funcionario.setDataAdmissao(LocalDate.of(2001, 5, 7));
        funcionario.setDataDemissao(LocalDate.of(2025, 11, 2));
        funcionario.setEnabled(true);
    }
}