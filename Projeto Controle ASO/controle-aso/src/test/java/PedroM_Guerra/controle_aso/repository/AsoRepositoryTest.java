package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.enums.ResultadoAso;
import PedroM_Guerra.controle_aso.enums.TipoAso;
import PedroM_Guerra.controle_aso.model.Aso;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
class AsoRepositoryTest {

    @Autowired
    AsoRepository repository;

    private static Aso aso;

    @BeforeAll
    static void setUp() {
        aso = new Aso();
    }

    @Test
    @Order(1)
    void existsByFuncionarioIdTest() {
        var result = repository.existsByFuncionarioId(1L);

        assertTrue(result);
    }

    @Test
    @Order(2)
    void existsByFuncionarioIdNotFoundTest() {
        var result = repository.existsByFuncionarioId(999999L);

        assertFalse(result);
    }

    @Test
    @Order(3)
    void findAsosByFuncionarioIdTest() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "id"));

        var page = repository.findAsosByFuncionarioId(1L, pageable);
        var asos = page.getContent();

        assertNotNull(page);
        assertFalse(asos.isEmpty());

        aso = asos.get(0);

        assertNotNull(aso);
        assertNotNull(aso.getId());
        assertNotNull(aso.getFuncionario());
        assertNotNull(aso.getFuncionario().getId());

        assertEquals(1L, aso.getFuncionario().getId());
        assertEquals("12345", aso.getCrmMedico());
        assertEquals("Dr. Carlos Macedo", aso.getNomeMedico());
        assertEquals("Exames de sangue", aso.getDescricaoExame());
        assertEquals("caminho-arquivo", aso.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, aso.getResultadoAso());
        assertEquals(TipoAso.ADMISSIONAL, aso.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 12), aso.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 12), aso.getDataValidade());

        assertTrue(aso.getEnabled());

        asos.forEach(asoEncontrado -> {
            assertNotNull(asoEncontrado);
            assertNotNull(asoEncontrado.getId());
            assertNotNull(asoEncontrado.getFuncionario());
            assertEquals(1L, asoEncontrado.getFuncionario().getId());
            assertTrue(asoEncontrado.getEnabled());
        });
    }

    @Test
    @Order(4)
    void findAsosByFuncionarioIdNotFoundTest() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "id"));

        var page = repository.findAsosByFuncionarioId(999999L, pageable);

        assertNotNull(page);
        assertTrue(page.getContent().isEmpty());
    }

    @Test
    @Order(5)
    void disableAsoTest() {
        Long id = aso.getId();

        repository.disableAso(id);

        var result = repository.findById(id);

        assertTrue(result.isPresent());

        var disabledAso = result.get();

        assertNotNull(disabledAso);
        assertNotNull(disabledAso.getId());

        assertEquals(id, disabledAso.getId());
        assertEquals(1L, disabledAso.getFuncionario().getId());
        assertEquals("12345", disabledAso.getCrmMedico());
        assertEquals("Dr. Carlos Macedo", disabledAso.getNomeMedico());
        assertEquals("Exames de sangue", disabledAso.getDescricaoExame());
        assertEquals("caminho-arquivo", disabledAso.getUrlDocumentoScan());

        assertEquals(ResultadoAso.APTO, disabledAso.getResultadoAso());
        assertEquals(TipoAso.ADMISSIONAL, disabledAso.getTipoAso());

        assertEquals(LocalDate.of(2026, 5, 12), disabledAso.getDataEmissao());
        assertEquals(LocalDate.of(2027, 5, 12), disabledAso.getDataValidade());

        assertFalse(disabledAso.getEnabled());
    }

}