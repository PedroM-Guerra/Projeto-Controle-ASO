package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.model.Aso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsoRepository extends JpaRepository<Aso, Long> {

    boolean existsByFuncionarioId(Long funcionarioId);

    @Query("SELECT a FROM Aso a WHERE a.funcionario.id = :funcionarioId")
    Page<Aso> findAsosByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
