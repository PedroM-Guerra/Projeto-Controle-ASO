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
import org.springframework.data.web.PagedResourcesAssembler;

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

    @Mock
    PagedResourcesAssembler<FuncionarioDTO> assembler; // Garanta que ele está declarado como @Mock

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
        assertFalse(result.getEnabled());
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
        assertFalse(result.getEnabled());
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
        assertFalse(result.getEnabled());
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
    void disable() {
        // 1. Cria a instância que simula o funcionário antes da desativação (Ativo)
        Funcionario funcionarioAtivo = input.mockEntity(2);
        funcionarioAtivo.setId(2L);
        funcionarioAtivo.setEnabled(true);

        // 2. Cria a instância que simula o funcionário após a query do banco (Inativo)
        Funcionario funcionarioInativo = input.mockEntity(2);
        funcionarioInativo.setId(2L);
        funcionarioInativo.setEnabled(false);

        // 3. Configura o findById para responder em sequência:
        // Primeira chamada retorna ativo, segunda chamada retorna inativo
        when(repository.findById(2L))
                .thenReturn(Optional.of(funcionarioAtivo))   // Resposta da primeira checagem
                .thenReturn(Optional.of(funcionarioInativo)); // Resposta após o repository.disableFuncionario

        // 4. Como o método disableFuncionario(id) do repositório provavelmente é @Modifying e retorna void, apenas simulamos a chamada
        doNothing().when(repository).disableFuncionario(2L);

        // 5. Executa a ação do serviço
        var result = service.disableFuncionario(2L);

        // 6. Garante que o retorno não é nulo e possui os links HATEOAS corretos
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/funcionario/v1/2")
                        && link.getType().equals("GET")
                ));

        // 7. Confirma se os dados estruturais batem com o MockFuncionario(2)
        assertEquals("Name Test2", result.getNome());
        assertEquals("CPF Test2", result.getCpf());
        assertEquals("Matricula Test2", result.getMatricula());

        // 8. Validação crucial: O segundo findById retornou falso, logo o DTO deve ser falso
        assertFalse(result.getEnabled());

        // 9. Verifica se as interações com o repositório aconteceram na ordem esperada
        verify(repository, times(2)).findById(2L);
        verify(repository, times(1)).disableFuncionario(2L);
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
    void findAll() {
        // 1. Cria a lista de entidades mockadas usando o seu helper
        List<Funcionario> list = input.mockEntityList();

        // 2. Configura os objetos de paginação do Spring Data
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 14);
        org.springframework.data.domain.Page<Funcionario> page = new org.springframework.data.domain.PageImpl<>(list, pageable, list.size());

        // 3. Configura o mock do repositório para receber o pageable correto
        when(repository.findAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        // 4. PREVENÇÃO DO NPE: Cria os EntityModels simulando o comportamento que o stream do service faz internamente
        List<org.springframework.hateoas.EntityModel<FuncionarioDTO>> entityModels = list.stream()
                .map(funcionario -> {
                    var dto = PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject(funcionario, FuncionarioDTO.class);
                    // Aplica a mesma lógica de HATEOAS que o service executa
                    dto.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(PedroM_Guerra.controle_aso.controllers.FuncionarioController.class).findById(dto.getId())).withSelfRel().withType("GET"));
                    dto.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(PedroM_Guerra.controle_aso.controllers.FuncionarioController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
                    return org.springframework.hateoas.EntityModel.of(dto);
                }).toList();

        org.springframework.hateoas.PagedModel<org.springframework.hateoas.EntityModel<FuncionarioDTO>> pagedModel =
                org.springframework.hateoas.PagedModel.of(entityModels, new org.springframework.hateoas.PagedModel.PageMetadata(14, 0, 14));

        // Stubbing crucial para evitar o NullPointerException no assembler.toModel
        when(assembler.toModel(any(org.springframework.data.domain.Page.class), any(org.springframework.hateoas.Link.class)))
                .thenReturn(pagedModel);

        // 5. Executa a chamada do método do serviço
        var result = service.findAll(pageable);

        // 6. Validações sobre o PagedModel retornado pelo assembler
        assertNotNull(result);

        List<FuncionarioDTO> funcionarios = result.getContent().stream()
                .map(org.springframework.hateoas.EntityModel::getContent)
                .toList();

        assertEquals(14, funcionarios.size());

        // --- VALIDAÇÃO DO FUNCIONÁRIO 1 (ÍMPAR -> enabled: false) ---
        var funcionarioOne = funcionarios.get(1);
        assertNotNull(funcionarioOne);
        assertNotNull(funcionarioOne.getId());
        assertNotNull(funcionarioOne.getLinks());
        assertEquals("Name Test1", funcionarioOne.getNome());
        assertEquals("CPF Test1", funcionarioOne.getCpf());
        assertFalse(funcionarioOne.getEnabled());

        // --- VALIDAÇÃO DO FUNCIONÁRIO 4 (PAR -> enabled: true) ---
        var funcionarioFour = funcionarios.get(4);
        assertNotNull(funcionarioFour);
        assertNotNull(funcionarioFour.getId());
        assertEquals("Name Test4", funcionarioFour.getNome());
        assertEquals("CPF Test4", funcionarioFour.getCpf());
        assertTrue(funcionarioFour.getEnabled());

        // --- VALIDAÇÃO DO FUNCIONÁRIO 7 (ÍMPAR -> enabled: false) ---
        var funcionarioSeven = funcionarios.get(7);
        assertNotNull(funcionarioSeven);
        assertEquals("Name Test7", funcionarioSeven.getNome());
        assertFalse(funcionarioSeven.getEnabled());
    }
}