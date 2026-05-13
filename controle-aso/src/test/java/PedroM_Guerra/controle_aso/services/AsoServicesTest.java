package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.enums.ResultadoAso;
import PedroM_Guerra.controle_aso.enums.TipoAso;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.model.Aso;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.repository.AsoRepository;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import PedroM_Guerra.controle_aso.unittests.mocks.MockAso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
        assertEquals(1L, result.getFuncionario().getId());

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

        assertEquals(1L, result.getFuncionario().getId());

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

        when(repository.findById(1L)).thenReturn(Optional.of(aso));
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

        assertEquals(1L, result.getFuncionario().getId());

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
        when(repository.findAll()).thenReturn(list);
        List<AsoDTO> asos = service.findAll();

        assertNotNull(asos);
        assertEquals(14, asos.size());

        var asoOne = asos.get(1);

        assertNotNull(asoOne);
        assertNotNull(asoOne.getId());
        assertNotNull(asoOne.getLinks());

        assertNotNull(asoOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(asoOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(asoOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(asoOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(asoOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals(1L, asoOne.getFuncionario().getId());

        assertEquals("CRM Test1", asoOne.getCrmMedico());
        assertEquals("Nome Medico Test1", asoOne.getNomeMedico());
        assertEquals("Descição Exame Test1", asoOne.getDescricaoExame());
        assertEquals("URL Test1", asoOne.getUrlDocumentoScan());

        assertEquals(ResultadoAso.INAPTO, asoOne.getResultadoAso());
        assertEquals(TipoAso.PERIODICO, asoOne.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 2), asoOne.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 2), asoOne.getDataValidade());

        var asoFour = asos.get(4);

        assertNotNull(asoFour);
        assertNotNull(asoFour.getId());
        assertNotNull(asoFour.getLinks());

        assertNotNull(asoFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(asoFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(asoFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(asoFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(asoFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals(4L, asoFour.getFuncionario().getId());

        assertEquals("CRM Test4", asoFour.getCrmMedico());
        assertEquals("Nome Medico Test4", asoFour.getNomeMedico());
        assertEquals("Descição Exame Test4", asoFour.getDescricaoExame());
        assertEquals("URL Test4", asoFour.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, asoFour.getResultadoAso());
        assertEquals(TipoAso.DEMISSIONAL, asoFour.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 5), asoFour.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 5), asoFour.getDataValidade());

        var asoSeven = asos.get(7);

        assertNotNull(asoSeven);
        assertNotNull(asoSeven.getId());
        assertNotNull(asoSeven.getLinks());

        assertNotNull(asoSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(asoSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(asoSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(asoSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/aso/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(asoSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/aso/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals(7L, asoSeven.getFuncionario().getId());

        assertEquals("CRM Test7", asoSeven.getCrmMedico());
        assertEquals("Nome Medico Test7", asoSeven.getNomeMedico());
        assertEquals("Descição Exame Test7", asoSeven.getDescricaoExame());
        assertEquals("URL Test7", asoSeven.getUrlDocumentoScan());

        assertEquals(ResultadoAso.INAPTO, asoSeven.getResultadoAso());
        assertEquals(TipoAso.RETORNO_TRABALHO, asoSeven.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 8), asoSeven.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 8), asoSeven.getDataValidade());
    }
}