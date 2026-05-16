package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.model.Aso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsoRepository extends JpaRepository<Aso, Long> {

    boolean existsByFuncionarioId(Long funcionarioId);
}
