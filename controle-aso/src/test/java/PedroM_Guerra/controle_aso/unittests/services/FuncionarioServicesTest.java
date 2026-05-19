package PedroM_Guerra.controle_aso.unittests.services;

import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.repository.AsoRepository;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import PedroM_Guerra.controle_aso.services.FuncionarioServices;
import PedroM_Guerra.controle_aso.unittests.mapper.mocks.MockFuncionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class FuncionarioServicesTest {

    MockFuncionario input;

    @InjectMocks
    private FuncionarioServices service;

    @Mock
    FuncionarioRepository repository;

    @Mock
    AsoRepository asoRepository;

    @BeforeEach
    void setUp() {
        input = new MockFuncionario();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Funcionario funcionario = input.mockEntity(1);
        funcionario.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(funcionario));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Name Test1", result.getNome());
        assertEquals("CPF Test1", result.getCpf());
        assertEquals("Matricula Test1", result.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, result.getGenero());

        assertEquals(CargoFuncionario.SUPERVISOR, result.getCargo());
        assertEquals(SetorFuncionario.LIMPEZA, result.getSetor());

        assertEquals(LocalDate.of(1981, 2, 2), result.getDataNascimento());
        assertEquals(LocalDate.of(1982, 3, 2), result.getDataAdmissao());
        assertNull(result.getDataDemissao());
    }

    @Test
    void create() {
        Funcionario funcionario = input.mockEntity(1);
        Funcionario persisted = funcionario;
        persisted.setId(1L);

        FuncionarioDTO dto = input.mockDTO(1);

        when(repository.save(funcionario)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Name Test1", result.getNome());
        assertEquals("CPF Test1", result.getCpf());
        assertEquals("Matricula Test1", result.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, result.getGenero());

        assertEquals(CargoFuncionario.SUPERVISOR, result.getCargo());
        assertEquals(SetorFuncionario.LIMPEZA, result.getSetor());

        assertEquals(LocalDate.of(1981, 2, 2), result.getDataNascimento());
        assertEquals(LocalDate.of(1982, 3, 2), result.getDataAdmissao());
        assertNull(result.getDataDemissao());
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
        Funcionario funcionario = input.mockEntity(1);
        Funcionario persisted = funcionario;
        persisted.setId(1L);

        FuncionarioDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(repository.save(funcionario)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Name Test1", result.getNome());
        assertEquals("CPF Test1", result.getCpf());
        assertEquals("Matricula Test1", result.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, result.getGenero());

        assertEquals(CargoFuncionario.SUPERVISOR, result.getCargo());
        assertEquals(SetorFuncionario.LIMPEZA, result.getSetor());

        assertEquals(LocalDate.of(1981, 2, 2), result.getDataNascimento());
        assertEquals(LocalDate.of(1982, 3, 2), result.getDataAdmissao());
        assertNull(result.getDataDemissao());
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
        Funcionario funcionario = input.mockEntity(1);
        funcionario.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(asoRepository.existsByFuncionarioId(1L)).thenReturn(false);

        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(asoRepository, times(1)).existsByFuncionarioId(1L);
        verify(repository, times(1)).delete(any(Funcionario.class));

        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(asoRepository);
    }

    @Test
    @Disabled("REASON: Still Under Development")
    void findAll() {
        List<Funcionario> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<FuncionarioDTO> funcionarios = new ArrayList<>(); //service.findAll(pageable);

        assertNotNull(funcionarios);
        assertEquals(14, funcionarios.size());

        var funcionarioOne = funcionarios.get(1);

        assertNotNull(funcionarioOne);
        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getLinks());

        assertNotNull(funcionarioOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(funcionarioOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(funcionarioOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(funcionarioOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(funcionarioOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Name Test1", funcionarioOne.getNome());
        assertEquals("CPF Test1", funcionarioOne.getCpf());
        assertEquals("Matricula Test1", funcionarioOne.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, funcionarioOne.getGenero());

        assertEquals(CargoFuncionario.SUPERVISOR, funcionarioOne.getCargo());
        assertEquals(SetorFuncionario.LIMPEZA, funcionarioOne.getSetor());

        assertEquals(LocalDate.of(1981, 2, 2), funcionarioOne.getDataNascimento());
        assertEquals(LocalDate.of(1982, 3, 2), funcionarioOne.getDataAdmissao());
        assertNull(funcionarioOne.getDataDemissao());

        var funcionarioFour = funcionarios.get(4);

        assertNotNull(funcionarioFour);
        assertNotNull(funcionarioFour.getId());
        assertNotNull(funcionarioFour.getLinks());

        assertNotNull(funcionarioFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(funcionarioFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(funcionarioFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(funcionarioFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(funcionarioFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Name Test4", funcionarioFour.getNome());
        assertEquals("CPF Test4", funcionarioFour.getCpf());
        assertEquals("Matricula Test4", funcionarioFour.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, funcionarioFour.getGenero());

        assertEquals(CargoFuncionario.TECNICO, funcionarioFour.getCargo());
        assertEquals(SetorFuncionario.ARQUITETURA, funcionarioFour.getSetor());

        assertEquals(LocalDate.of(1984, 5, 5), funcionarioFour.getDataNascimento());
        assertEquals(LocalDate.of(1985, 9, 5), funcionarioFour.getDataAdmissao());
        assertEquals(LocalDate.of(1985, 10, 5), funcionarioFour.getDataDemissao());

        var funcionarioSeven = funcionarios.get(7);

        assertNotNull(funcionarioSeven);
        assertNotNull(funcionarioSeven.getId());
        assertNotNull(funcionarioSeven.getLinks());

        assertNotNull(funcionarioSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(funcionarioSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(funcionarioSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(funcionarioSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/funcionario/v1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(funcionarioSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/funcionario/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Name Test7", funcionarioSeven.getNome());
        assertEquals("CPF Test7", funcionarioSeven.getCpf());
        assertEquals("Matricula Test7", funcionarioSeven.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, funcionarioSeven.getGenero());

        assertEquals(CargoFuncionario.OPERARIO, funcionarioSeven.getCargo());
        assertEquals(SetorFuncionario.SAUDE_TRABALHO, funcionarioSeven.getSetor());

        assertEquals(LocalDate.of(1987, 8, 8), funcionarioSeven.getDataNascimento());
        assertEquals(LocalDate.of(1989, 3, 8), funcionarioSeven.getDataAdmissao());
        assertNull(funcionarioSeven.getDataDemissao());
    }
}