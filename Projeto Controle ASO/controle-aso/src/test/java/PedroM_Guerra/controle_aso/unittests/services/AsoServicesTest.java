package PedroM_Guerra.controle_aso.unittests.services;

import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.enums.ResultadoAso;
import PedroM_Guerra.controle_aso.enums.TipoAso;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.model.Aso;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.repository.AsoRepository;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import PedroM_Guerra.controle_aso.services.AsoServices;
import PedroM_Guerra.controle_aso.unittests.mapper.mocks.MockAso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PagedResourcesAssembler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AsoServicesTest {

    MockAso input;

    @InjectMocks
    private AsoServices service;

    @Mock
    AsoRepository repository;

    @Mock
    FuncionarioRepository funcionarioRepository;

    @Mock
    PagedResourcesAssembler<AsoDTO> assembler;

    @BeforeEach
    void setUp() {
        input = new MockAso();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Aso aso = input.mockEntity(1);
        aso.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(aso));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("DELETE")
                ));

        //assertNotNull(result.getFuncionario());
        assertEquals(1L, result.getFuncionarioId());

        assertEquals("CRM Test1", result.getCrmMedico());
        assertEquals("Nome Medico Test1", result.getNomeMedico());
        assertEquals("Descição Exame Test1", result.getDescricaoExame());
        assertEquals("URL Test1", result.getUrlDocumentoScan());

        //assertNotNull(result.getTipoAso());
        assertEquals(ResultadoAso.INAPTO, result.getResultadoAso());
        assertEquals(TipoAso.PERIODICO, result.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 2), result.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 2), result.getDataValidade());
    }

    @Test
    void create() {

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        Aso aso = input.mockEntity(1);
        Aso persisted = aso;
        persisted.setId(1L);

        AsoDTO dto = input.mockDTO(1);

        when(repository.save(any(Aso.class))).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals(1L, result.getFuncionarioId());

        assertEquals("CRM Test1", result.getCrmMedico());
        assertEquals("Nome Medico Test1", result.getNomeMedico());
        assertEquals("Descição Exame Test1", result.getDescricaoExame());
        assertEquals("URL Test1", result.getUrlDocumentoScan());

        assertEquals(ResultadoAso.INAPTO, result.getResultadoAso());
        assertEquals(TipoAso.PERIODICO, result.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 2), result.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 2), result.getDataValidade());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
        () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Aso aso = input.mockEntity(1);
        Aso persisted = aso;
        persisted.setId(1L);

        AsoDTO dto = input.mockDTO(1);
        dto.setId(1L);
        dto.setFuncionarioId(1L);

        Funcionario funcionarioMock = new Funcionario();
        funcionarioMock.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(aso));
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioMock));
        when(repository.save(aso)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals(1L, result.getFuncionarioId());

        assertEquals("CRM Test1", result.getCrmMedico());
        assertEquals("Nome Medico Test1", result.getNomeMedico());
        assertEquals("Descição Exame Test1", result.getDescricaoExame());
        assertEquals("URL Test1", result.getUrlDocumentoScan());

        assertEquals(ResultadoAso.INAPTO, result.getResultadoAso());
        assertEquals(TipoAso.PERIODICO, result.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 2), result.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 2), result.getDataValidade());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Aso aso = input.mockEntity(1);
        aso.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(aso));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Aso.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Aso> list = input.mockEntityList();

        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 14);
        org.springframework.data.domain.Page<Aso> page = new org.springframework.data.domain.PageImpl<>(list, pageable, list.size());

        when(repository.findAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        List<org.springframework.hateoas.EntityModel<AsoDTO>> entityModels = list.stream()
                .map(aso -> {
                    var dto = PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject(aso, AsoDTO.class);
                    dto.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(PedroM_Guerra.controle_aso.controllers.AsoController.class).findById(dto.getId())).withSelfRel().withType("GET"));
                    return org.springframework.hateoas.EntityModel.of(dto);
                }).toList();

        org.springframework.hateoas.PagedModel<org.springframework.hateoas.EntityModel<AsoDTO>> pagedModel =
                org.springframework.hateoas.PagedModel.of(entityModels, new org.springframework.hateoas.PagedModel.PageMetadata(14, 0, 14));

        when(assembler.toModel(any(org.springframework.data.domain.Page.class), any(org.springframework.hateoas.Link.class)))
                .thenReturn(pagedModel);

        var result = service.findAll(pageable);

        assertNotNull(result);

        List<AsoDTO> asos = result.getContent().stream()
                .map(org.springframework.hateoas.EntityModel::getContent)
                .toList();

        assertEquals(14, asos.size());

        // --- VALIDAÇÃO DO ASO 1 (ÍMPAR -> enabled: false, Resultado: INAPTO) ---
        var asoOne = asos.get(1);
        assertNotNull(asoOne);
        assertNotNull(asoOne.getId());
        assertNotNull(asoOne.getLinks());
        assertEquals("CRM Test1", asoOne.getCrmMedico());
        assertEquals("Nome Medico Test1", asoOne.getNomeMedico());
        assertEquals(TipoAso.PERIODICO, asoOne.getTipoAso());
        assertEquals(ResultadoAso.INAPTO, asoOne.getResultadoAso());
        assertFalse(asoOne.getEnabled());

        // --- VALIDAÇÃO DO ASO 4 (PAR -> enabled: true, Resultado: APTO) ---
        var asoFour = asos.get(4);
        assertNotNull(asoFour);
        assertNotNull(asoFour.getId());
        assertEquals("CRM Test4", asoFour.getCrmMedico());
        assertEquals("Nome Medico Test4", asoFour.getNomeMedico());

        assertEquals(TipoAso.DEMISSIONAL, asoFour.getTipoAso());

        assertEquals(ResultadoAso.APTO, asoFour.getResultadoAso());
        assertTrue(asoFour.getEnabled());

        // --- VALIDAÇÃO DO ASO 7 (ÍMPAR -> enabled: false, Resultado: INAPTO) ---
        var asoSeven = asos.get(7);
        assertNotNull(asoSeven);
        assertEquals("CRM Test7", asoSeven.getCrmMedico());
        assertEquals("Nome Medico Test7", asoSeven.getNomeMedico());
        assertEquals(TipoAso.RETORNO_TRABALHO, asoSeven.getTipoAso());
        assertEquals(ResultadoAso.INAPTO, asoSeven.getResultadoAso());
        assertFalse(asoSeven.getEnabled());
    }
}