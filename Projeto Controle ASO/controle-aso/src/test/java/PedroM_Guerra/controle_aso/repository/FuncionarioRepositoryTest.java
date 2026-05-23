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
        assertEquals("Larissa Santos", funcionario.getNome());
        assertEquals("1236661385", funcionario.getCpf());
        assertEquals("15649", funcionario.getMatricula());
        assertEquals(LocalDate.of(1982, 6, 7), funcionario.getDataNascimento());
        assertEquals(GeneroFuncionario.FEMININO, funcionario.getGenero());
        assertEquals(SetorFuncionario.ARQUITETURA, funcionario.getSetor());
        assertEquals(CargoFuncionario.ARQUITETO, funcionario.getCargo());
        assertEquals(LocalDate.of(2009, 12, 5), funcionario.getDataAdmissao());
        assertEquals(LocalDate.of(2026, 3, 11), funcionario.getDataDemissao());
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
        assertEquals("Larissa Santos", funcionario.getNome());
        assertEquals("1236661385", funcionario.getCpf());
        assertEquals("15649", funcionario.getMatricula());
        assertEquals(LocalDate.of(1982, 6, 7), funcionario.getDataNascimento());
        assertEquals(GeneroFuncionario.FEMININO, funcionario.getGenero());
        assertEquals(SetorFuncionario.ARQUITETURA, funcionario.getSetor());
        assertEquals(CargoFuncionario.ARQUITETO, funcionario.getCargo());
        assertEquals(LocalDate.of(2009, 12, 5), funcionario.getDataAdmissao());
        assertEquals(LocalDate.of(2026, 3, 11), funcionario.getDataDemissao());
        assertFalse(funcionario.getEnabled());
    }
}