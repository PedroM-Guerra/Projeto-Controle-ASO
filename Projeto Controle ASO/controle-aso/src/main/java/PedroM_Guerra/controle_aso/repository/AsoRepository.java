package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.model.Aso;
import PedroM_Guerra.controle_aso.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsoRepository extends JpaRepository<Aso, Long> {

    boolean existsByFuncionarioId(Long funcionarioId);

    @Query("SELECT a FROM Aso a WHERE a.funcionario.id = :funcionarioId AND a.enabled = true")
    Page<Aso> findAsosByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Aso a SET a.enabled = false WHERE a.id =:id")
    void disableAso(@Param("id") Long id);

}
