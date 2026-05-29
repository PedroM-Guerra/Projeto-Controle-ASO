package PedroM_Guerra.controle_aso.integrationtests.controllers.withjson;

import PedroM_Guerra.controle_aso.config.TestConfigs;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.integrationtests.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.integrationtests.dto.wrappers.WrapperFuncionarioDTO;
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
import java.util.Map;

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
        assertEquals("02334378518", createdFuncionario.getCpf());
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
        assertEquals("02334378518", createdFuncionario.getCpf());
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
        assertEquals("02334378518", createdFuncionario.getCpf());
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
        assertEquals("02334378518", createdFuncionario.getCpf());
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
                .queryParams("page", 3, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperFuncionarioDTO wrapper = objectMapper.readValue(content, WrapperFuncionarioDTO.class);
        List<FuncionarioDTO> funcionarios = wrapper.getEmbeded().getFuncionarios();

        FuncionarioDTO funcionarioOne = funcionarios.get(0);

        assertNotNull(funcionarioOne.getId());
        assertTrue(funcionarioOne.getId() > 0);

        assertEquals("Antônio Tavares", funcionarioOne.getNome());
        assertEquals("00002470728", funcionarioOne.getCpf());
        assertEquals("70728", funcionarioOne.getMatricula());
        assertEquals(LocalDate.of(1970, 1, 8), funcionarioOne.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioOne.getGenero());
        assertEquals(SetorFuncionario.ENGENHARIA, funcionarioOne.getSetor());
        assertEquals(CargoFuncionario.MEDICO, funcionarioOne.getCargo());
        assertEquals(LocalDate.of(2010, 9, 4), funcionarioOne.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 2, 2), funcionarioOne.getDataDemissao());
        assertTrue(funcionarioOne.getEnabled());

        FuncionarioDTO funcionarioFour = funcionarios.get(4);

        assertNotNull(funcionarioFour.getId());
        assertTrue(funcionarioFour.getId() > 0);

        assertEquals("Arthur Miranda", funcionarioFour.getNome());
        assertEquals("00007459698", funcionarioFour.getCpf());
        assertEquals("59698", funcionarioFour.getMatricula());
        assertEquals(LocalDate.of(1958, 12, 3), funcionarioFour.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioFour.getGenero());
        assertEquals(SetorFuncionario.SAUDE_TRABALHO, funcionarioFour.getSetor());
        assertEquals(CargoFuncionario.OPERARIO, funcionarioFour.getCargo());
        assertEquals(LocalDate.of(1995, 10, 27), funcionarioFour.getDataAdmissao());
        assertNull(funcionarioFour.getDataDemissao());
        assertTrue(funcionarioFour.getEnabled());
    }

    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {
        //{{baseUrl}}/api/funcionario/v1/findFuncionarioByName/and?page=0&size=12&direction=asc
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("nome", "and")
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("findFuncionarioByName/{nome}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperFuncionarioDTO wrapper = objectMapper.readValue(content, WrapperFuncionarioDTO.class);
        List<FuncionarioDTO> funcionarios = wrapper.getEmbeded().getFuncionarios();

        FuncionarioDTO funcionarioOne = funcionarios.get(0);

        assertNotNull(funcionarioOne.getId());
        assertTrue(funcionarioOne.getId() > 0);

        assertEquals("André Aguiar", funcionarioOne.getNome());
        assertEquals("00001282878", funcionarioOne.getCpf());
        assertEquals("82878", funcionarioOne.getMatricula());
        assertEquals(LocalDate.of(1965, 3, 2), funcionarioOne.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioOne.getGenero());
        assertEquals(SetorFuncionario.ARQUITETURA, funcionarioOne.getSetor());
        assertEquals(CargoFuncionario.DIRETOR, funcionarioOne.getCargo());
        assertEquals(LocalDate.of(1989, 11, 2), funcionarioOne.getDataAdmissao());
        assertNull(funcionarioOne.getDataDemissao());
        assertTrue(funcionarioOne.getEnabled());

        FuncionarioDTO funcionarioFour = funcionarios.get(4);

        assertNotNull(funcionarioFour.getId());
        assertTrue(funcionarioFour.getId() > 0);

        assertEquals("André Miranda", funcionarioFour.getNome());
        assertEquals("00005155269", funcionarioFour.getCpf());
        assertEquals("55269", funcionarioFour.getMatricula());
        assertEquals(LocalDate.of(1957, 3, 1), funcionarioFour.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioFour.getGenero());
        assertEquals(SetorFuncionario.OPERACIONAL, funcionarioFour.getSetor());
        assertEquals(CargoFuncionario.ARQUITETO, funcionarioFour.getCargo());
        assertEquals(LocalDate.of(2006, 5, 14), funcionarioFour.getDataAdmissao());
        assertEquals(LocalDate.of(2025, 8, 15), funcionarioFour.getDataDemissao());
        assertTrue(funcionarioFour.getEnabled());
    }

    @Test
    @Order(8)
    void getGenerosTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("generos")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<Map<String, String>> generos = objectMapper.readValue(
                content,
                new TypeReference<List<Map<String, String>>>() {}
        );

        assertNotNull(generos);
        assertEquals(GeneroFuncionario.values().length, generos.size());

        for (GeneroFuncionario generoFuncionario : GeneroFuncionario.values()) {
            Map<String, String> generoEncontrado = generos.stream()
                    .filter(genero -> generoFuncionario.getCodigo().equals(genero.get("codigo")))
                    .findFirst()
                    .orElse(null);

            assertNotNull(generoEncontrado);
            assertEquals(generoFuncionario.getCodigo(), generoEncontrado.get("codigo"));
            assertEquals(generoFuncionario.getDescricao(), generoEncontrado.get("descricao"));
        }
    }

    @Test
    @Order(9)
    void getSetoresTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("setores")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<Map<String, String>> setores = objectMapper.readValue(
                content,
                new TypeReference<List<Map<String, String>>>() {}
        );

        assertNotNull(setores);
        assertEquals(SetorFuncionario.values().length, setores.size());

        for (SetorFuncionario setorFuncionario : SetorFuncionario.values()) {
            Map<String, String> setorEncontrado = setores.stream()
                    .filter(setor -> setorFuncionario.getCodigo().equals(setor.get("codigo")))
                    .findFirst()
                    .orElse(null);

            assertNotNull(setorEncontrado);
            assertEquals(setorFuncionario.getCodigo(), setorEncontrado.get("codigo"));
            assertEquals(setorFuncionario.getDescricao(), setorEncontrado.get("descricao"));
        }
    }

    @Test
    @Order(10)
    void getCargosTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("cargos")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<Map<String, String>> cargos = objectMapper.readValue(
                content,
                new TypeReference<List<Map<String, String>>>() {}
        );

        assertNotNull(cargos);
        assertEquals(CargoFuncionario.values().length, cargos.size());

        for (CargoFuncionario cargoFuncionario : CargoFuncionario.values()) {
            Map<String, String> cargoEncontrado = cargos.stream()
                    .filter(cargo -> cargoFuncionario.getCodigo().equals(cargo.get("codigo")))
                    .findFirst()
                    .orElse(null);

            assertNotNull(cargoEncontrado);
            assertEquals(cargoFuncionario.getCodigo(), cargoEncontrado.get("codigo"));
            assertEquals(cargoFuncionario.getDescricao(), cargoEncontrado.get("descricao"));
        }
    }

    private void mockFuncionario() {
        funcionario.setNome("Marcielly Gislayne");
        funcionario.setCpf("02334378518");
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