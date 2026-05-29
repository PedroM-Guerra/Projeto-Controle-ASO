package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.model.Funcionario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FuncionarioRepositoryTest {

    @Autowired
    FuncionarioRepository repository;

    private static Funcionario funcionario;

    @BeforeAll
    static void setUp() {
        funcionario = new Funcionario();
    }

    @Test
    @Order(1)
    void findFuncionariosByName() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "nome"));

        funcionario = repository.FindFuncionariosByName("sant", pageable).getContent().get(2);

        assertNotNull(funcionario);
        assertNotNull(funcionario.getId());
        assertEquals("Igor Santana", funcionario.getNome());
        assertEquals("00003896148", funcionario.getCpf());
        assertEquals("96148", funcionario.getMatricula());
        assertEquals(LocalDate.of(1974, 10, 24), funcionario.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionario.getGenero());
        assertEquals(SetorFuncionario.SAUDE_TRABALHO, funcionario.getSetor());
        assertEquals(CargoFuncionario.ARQUITETO, funcionario.getCargo());
        assertEquals(LocalDate.of(2010, 2, 17), funcionario.getDataAdmissao());
        assertEquals(LocalDate.of(2026, 3, 10), funcionario.getDataDemissao());
        assertTrue(funcionario.getEnabled());
    }

    @Test
    @Order(2)
    void disableFuncionario() {

        Long id = funcionario.getId();
        repository.disableFuncionario(id);

        var result = repository.findById(id);
        funcionario = result.get();

        assertNotNull(funcionario);
        assertNotNull(funcionario.getId());
        assertEquals("Igor Santana", funcionario.getNome());
        assertEquals("00003896148", funcionario.getCpf());
        assertEquals("96148", funcionario.getMatricula());
        assertEquals(LocalDate.of(1974, 10, 24), funcionario.getDataNascimento());
        assertEquals(GeneroFuncionario.MASCULINO, funcionario.getGenero());
        assertEquals(SetorFuncionario.SAUDE_TRABALHO, funcionario.getSetor());
        assertEquals(CargoFuncionario.ARQUITETO, funcionario.getCargo());
        assertEquals(LocalDate.of(2010, 2, 17), funcionario.getDataAdmissao());
        assertEquals(LocalDate.of(2026, 3, 10), funcionario.getDataDemissao());
        assertFalse(funcionario.getEnabled());
    }

    @Test
    @Order(3)
    void findFuncionariosByEnabledTrue() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "nome"));

        var page = repository.findFuncionariosByEnabledTrue(pageable);
        var funcionarios = page.getContent();

        assertNotNull(page);
        assertFalse(funcionarios.isEmpty());

        funcionarios.forEach(funcionario -> {
            assertNotNull(funcionario);
            assertNotNull(funcionario.getId());
            assertNotNull(funcionario.getNome());
            assertNotNull(funcionario.getCpf());
            assertNotNull(funcionario.getMatricula());
            assertNotNull(funcionario.getDataNascimento());
            assertNotNull(funcionario.getGenero());
            assertNotNull(funcionario.getSetor());
            assertNotNull(funcionario.getCargo());
            assertNotNull(funcionario.getDataAdmissao());
            assertTrue(funcionario.getEnabled());
        });
    }

    @Test
    @Order(4)
    void findByCpf() {
        Pageable pageable = PageRequest.of(
                0,
                1,
                Sort.by(Sort.Direction.ASC, "nome"));

        var funcionarioSalvo = repository.findFuncionariosByEnabledTrue(pageable)
                .getContent()
                .get(0);

        var result = repository.findByCpf(funcionarioSalvo.getCpf());

        assertTrue(result.isPresent());

        var funcionarioEncontrado = result.get();

        assertNotNull(funcionarioEncontrado);
        assertNotNull(funcionarioEncontrado.getId());

        assertEquals(funcionarioSalvo.getId(), funcionarioEncontrado.getId());
        assertEquals(funcionarioSalvo.getNome(), funcionarioEncontrado.getNome());
        assertEquals(funcionarioSalvo.getCpf(), funcionarioEncontrado.getCpf());
        assertEquals(funcionarioSalvo.getMatricula(), funcionarioEncontrado.getMatricula());
        assertEquals(funcionarioSalvo.getDataNascimento(), funcionarioEncontrado.getDataNascimento());
        assertEquals(funcionarioSalvo.getGenero(), funcionarioEncontrado.getGenero());
        assertEquals(funcionarioSalvo.getSetor(), funcionarioEncontrado.getSetor());
        assertEquals(funcionarioSalvo.getCargo(), funcionarioEncontrado.getCargo());
        assertEquals(funcionarioSalvo.getDataAdmissao(), funcionarioEncontrado.getDataAdmissao());
        assertEquals(funcionarioSalvo.getDataDemissao(), funcionarioEncontrado.getDataDemissao());
        assertEquals(funcionarioSalvo.getEnabled(), funcionarioEncontrado.getEnabled());
    }
}