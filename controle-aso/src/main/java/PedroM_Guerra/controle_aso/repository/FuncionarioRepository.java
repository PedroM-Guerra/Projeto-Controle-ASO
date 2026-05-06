package PedroM_Guerra.controle_aso.repository;

import PedroM_Guerra.controle_aso.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
