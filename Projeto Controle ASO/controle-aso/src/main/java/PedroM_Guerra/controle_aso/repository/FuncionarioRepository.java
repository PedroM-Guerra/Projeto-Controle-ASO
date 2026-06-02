package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Funcionario f SET f.enabled = false WHERE f.id =:id")
    void disableFuncionario(@Param("id") Long id);

    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND f.enabled = true " +
            "ORDER BY LOCATE(LOWER(:nome), LOWER(f.nome)) ASC, f.nome ASC")
    Page<Funcionario> FindFuncionariosByName(@Param("nome") String nome, Pageable pageable);

    Page<Funcionario> findFuncionariosByEnabledTrue(Pageable pageable);

    Optional<Funcionario> findByCpf(String cpf);

    @Query("SELECT f FROM Funcionario f WHERE f.enabled = true AND f.id IN (" +
            "  SELECT a.funcionario.id FROM Aso a " +
            "  WHERE a.enabled = true " +
            "  GROUP BY a.funcionario.id " +
            "  HAVING MAX(a.dataValidade) < :hoje" +
            ")")
    Page<Funcionario> findFuncionariosComAsoMaisRecenteVencido(@Param("hoje") LocalDate hoje, Pageable pageable);

    @Query("SELECT f FROM Funcionario f WHERE f.enabled = true AND f.id IN (" +
            "  SELECT a.funcionario.id FROM Aso a " +
            "  WHERE a.enabled = true " +
            "  GROUP BY a.funcionario.id " +
            "  HAVING MAX(a.dataValidade) >= :amanha AND MAX(a.dataValidade) <= :dataLimite" +
            ")")
    Page<Funcionario> findFuncionariosComAsoPertoDeVencer(
            @Param("amanha") LocalDate amanha,
            @Param("dataLimite") LocalDate dataLimite,
            Pageable pageable);
}
