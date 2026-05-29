package PedroM_Guerra.controle_aso.integrationtests.controllers.withjson;

import PedroM_Guerra.controle_aso.config.TestConfigs;
import PedroM_Guerra.controle_aso.enums.*;
import PedroM_Guerra.controle_aso.integrationtests.dto.AsoDTO;
import PedroM_Guerra.controle_aso.integrationtests.dto.wrappers.WrapperAsoDTO;
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
class AsoControllerJsonTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static AsoDTO aso;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        aso = new AsoDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockASO();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .setBasePath("/api/aso/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(aso)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        AsoDTO createdASO = objectMapper.readValue(content, AsoDTO.class);
        aso = createdASO;

        assertNotNull(createdASO.getId());
        assertTrue(createdASO.getId() > 0);

        assertEquals(1L, createdASO.getFuncionarioId());
        assertEquals("56781", createdASO.getCrmMedico());
        assertEquals("Dr. Lucas Barros", createdASO.getNomeMedico());
        assertEquals("Exame clinico hemograma e glicemia", createdASO.getDescricaoExame());
        assertEquals("documentos/aso/funcionario-teste1-admissional.pdf", createdASO.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, createdASO.getResultadoAso());
        assertEquals(TipoAso.RETORNO_TRABALHO, createdASO.getTipoAso());

        assertEquals(LocalDate.of(2025, 5, 2), createdASO.getDataEmissao());
        assertEquals(LocalDate.of(2026, 5, 2), createdASO.getDataValidade());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        aso.setNomeMedico("Dra. Marciely Gislaine");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(aso)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        AsoDTO createdASO = objectMapper.readValue(content, AsoDTO.class);
        aso = createdASO;

        assertNotNull(createdASO.getId());
        assertTrue(createdASO.getId() > 0);

        assertEquals(1L, createdASO.getFuncionarioId());
        assertEquals("56781", createdASO.getCrmMedico());
        assertEquals("Dra. Marciely Gislaine", createdASO.getNomeMedico());
        assertEquals("Exame clinico hemograma e glicemia", createdASO.getDescricaoExame());
        assertEquals("documentos/aso/funcionario-teste1-admissional.pdf", createdASO.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, createdASO.getResultadoAso());
        assertEquals(TipoAso.RETORNO_TRABALHO, createdASO.getTipoAso());

        assertEquals(LocalDate.of(2025, 5, 2), createdASO.getDataEmissao());
        assertEquals(LocalDate.of(2026, 5, 2), createdASO.getDataValidade());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", aso.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        AsoDTO createdASO = objectMapper.readValue(content, AsoDTO.class);
        aso = createdASO;

        assertNotNull(createdASO.getId());
        assertTrue(createdASO.getId() > 0);

        assertEquals(1L, createdASO.getFuncionarioId());
        assertEquals("56781", createdASO.getCrmMedico());
        assertEquals("Dra. Marciely Gislaine", createdASO.getNomeMedico());
        assertEquals("Exame clinico hemograma e glicemia", createdASO.getDescricaoExame());
        assertEquals("documentos/aso/funcionario-teste1-admissional.pdf", createdASO.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, createdASO.getResultadoAso());
        assertEquals(TipoAso.RETORNO_TRABALHO, createdASO.getTipoAso());

        assertEquals(LocalDate.of(2025, 5, 2), createdASO.getDataEmissao());
        assertEquals(LocalDate.of(2026, 5, 2), createdASO.getDataValidade());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", aso.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        AsoDTO createdASO = objectMapper.readValue(content, AsoDTO.class);
        aso = createdASO;

        assertNotNull(createdASO.getId());
        assertTrue(createdASO.getId() > 0);

        assertEquals(1L, createdASO.getFuncionarioId());
        assertEquals("56781", createdASO.getCrmMedico());
        assertEquals("Dra. Marciely Gislaine", createdASO.getNomeMedico());
        assertEquals("Exame clinico hemograma e glicemia", createdASO.getDescricaoExame());
        assertEquals("documentos/aso/funcionario-teste1-admissional.pdf", createdASO.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, createdASO.getResultadoAso());
        assertEquals(TipoAso.RETORNO_TRABALHO, createdASO.getTipoAso());

        assertEquals(LocalDate.of(2025, 5, 2), createdASO.getDataEmissao());
        assertEquals(LocalDate.of(2026, 5, 2), createdASO.getDataValidade());
        assertFalse(createdASO.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", aso.getId())
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

        WrapperAsoDTO wrapper = objectMapper.readValue(content, WrapperAsoDTO.class);
        List<AsoDTO> asos = wrapper.getEmbedded().getAsos();

        AsoDTO asoOne = asos.get(0);

        assertNotNull(asoOne.getId());
        assertTrue(asoOne.getId() > 0);


        assertEquals(350L, asoOne.getFuncionarioId());
        assertEquals("34561", asoOne.getCrmMedico());
        assertEquals("Dr. Felipe Nogueira", asoOne.getNomeMedico());
        assertEquals("Avaliacao medica ocupacional", asoOne.getDescricaoExame());
        assertEquals("1779994573324-ASOFicticio.pdf", asoOne.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, asoOne.getResultadoAso());
        assertEquals(TipoAso.MUDANCA_FUNCAO, asoOne.getTipoAso());

        assertEquals(LocalDate.of(2023, 2, 17), asoOne.getDataEmissao());
        assertEquals(LocalDate.of(2023, 8, 16), asoOne.getDataValidade());

        AsoDTO asoFour = asos.get(4);

        assertNotNull(asoFour.getId());
        assertTrue(asoFour.getId() > 0);

        assertEquals(614L, asoFour.getFuncionarioId());
        assertEquals("34562", asoFour.getCrmMedico());
        assertEquals("Dra. Helena Ribeiro", asoFour.getNomeMedico());
        assertEquals("Exame periodico ocupacional", asoFour.getDescricaoExame());
        assertEquals("1779994573324-ASOFicticio.pdf", asoFour.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, asoFour.getResultadoAso());
        assertEquals(TipoAso.DEMISSIONAL, asoFour.getTipoAso());

        assertEquals(LocalDate.of(2023, 2, 27), asoFour.getDataEmissao());
        assertEquals(LocalDate.of(2024, 4, 2), asoFour.getDataValidade());
    }

    @Test
    @Order(7)
    void findAsosByFuncionarioId() throws JsonProcessingException {
        //{{baseUrl}}/api/aso/v1/findAsoByFuncionarioId/1?page=0&size=12&direction=asc
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("funcionarioId", 1L)
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("findAsoByFuncionarioId/{funcionarioId}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperAsoDTO wrapper = objectMapper.readValue(content, WrapperAsoDTO.class);
        List<AsoDTO> asos = wrapper.getEmbedded().getAsos();

        AsoDTO asoOne = asos.get(0);

        assertNotNull(asoOne.getId());
        assertTrue(asoOne.getId() > 0);


        assertEquals(1L, asoOne.getFuncionarioId());
        assertEquals("12345", asoOne.getCrmMedico());
        assertEquals("Dr. Carlos Macedo", asoOne.getNomeMedico());
        assertEquals("Exames de sangue", asoOne.getDescricaoExame());
        assertEquals("caminho-arquivo", asoOne.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, asoOne.getResultadoAso());
        assertEquals(TipoAso.ADMISSIONAL, asoOne.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 12), asoOne.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 12), asoOne.getDataValidade());

        AsoDTO asoTwo = asos.get(2);

        assertNotNull(asoTwo.getId());
        assertTrue(asoTwo.getId() > 0);

        assertEquals(1L, asoTwo.getFuncionarioId());
        assertEquals("12346", asoTwo.getCrmMedico());
        assertEquals("Dra. Joana Andrade", asoTwo.getNomeMedico());
        assertEquals("Exame de audiometria", asoTwo.getDescricaoExame());
        assertEquals("1779994573324-ASOFicticio.pdf", asoTwo.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, asoTwo.getResultadoAso());
        assertEquals(TipoAso.ADMISSIONAL, asoTwo.getTipoAso());

        assertEquals(LocalDate.of(2024, 7, 17), asoTwo.getDataEmissao());
        assertEquals(LocalDate.of(2025, 1, 13), asoTwo.getDataValidade());
    }

    @Test
    @Order(8)
    void getTiposAsoTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("tipos")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<Map<String, String>> tipos = objectMapper.readValue(
                content,
                new TypeReference<List<Map<String, String>>>() {}
        );

        assertNotNull(tipos);
        assertEquals(TipoAso.values().length, tipos.size());

        for (TipoAso tipoAso : TipoAso.values()) {
            Map<String, String> tipoEncontrado = tipos.stream()
                    .filter(tipo -> tipoAso.getCodigo().equals(tipo.get("codigo")))
                    .findFirst()
                    .orElse(null);

            assertNotNull(tipoEncontrado);
            assertEquals(tipoAso.getCodigo(), tipoEncontrado.get("codigo"));
            assertEquals(tipoAso.getDescricao(), tipoEncontrado.get("descricao"));
        }
    }

    @Test
    @Order(9)
    void getResultadosAsoTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("resultados")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<Map<String, String>> resultados = objectMapper.readValue(
                content,
                new TypeReference<List<Map<String, String>>>() {}
        );

        assertNotNull(resultados);
        assertEquals(ResultadoAso.values().length, resultados.size());

        for (ResultadoAso resultadoAso : ResultadoAso.values()) {
            Map<String, String> resultadoEncontrado = resultados.stream()
                    .filter(resultado -> resultadoAso.getCodigo().equals(resultado.get("codigo")))
                    .findFirst()
                    .orElse(null);

            assertNotNull(resultadoEncontrado);
            assertEquals(resultadoAso.getCodigo(), resultadoEncontrado.get("codigo"));
            assertEquals(resultadoAso.getDescricao(), resultadoEncontrado.get("descricao"));
        }
    }
    
    
    private void mockASO() {
        aso.setFuncionarioId(1L);
        aso.setCrmMedico("56781");
        aso.setNomeMedico("Dr. Lucas Barros");
        aso.setDescricaoExame("Exame clinico hemograma e glicemia");
        aso.setUrlDocumentoScan("documentos/aso/funcionario-teste1-admissional.pdf");

        aso.setResultadoAso(ResultadoAso.APTO);
        aso.setTipoAso(TipoAso.RETORNO_TRABALHO);

        aso.setDataEmissao(LocalDate.of(2025, 5, 2));
        aso.setDataValidade(LocalDate.of(2026, 5, 2));

        aso.setEnabled(true);
    }
}