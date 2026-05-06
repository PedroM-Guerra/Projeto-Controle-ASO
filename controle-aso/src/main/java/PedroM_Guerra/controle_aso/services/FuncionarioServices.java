package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.exception.ResourceNotFoundException;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class FuncionarioServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(FuncionarioServices.class.getName());

    @Autowired
    FuncionarioRepository repository;

    public List<Funcionario> findAll(){
        logger.info("Finding all Funcionários");

        return repository.findAll();
    }

    public Funcionario findById(Long id){
        logger.info("Finding one Funcionário");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public Funcionario create(Funcionario funcionario){
        logger.info("Creating one Funcionário");

        return repository.save(funcionario);
    }

    public Funcionario update(Funcionario funcionario){
        logger.info("Updating one Funcionário");

        Funcionario entity = repository.findById(funcionario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setNome(funcionario.getNome());
        entity.setCpf(funcionario.getCpf());
        entity.setMatricula(funcionario.getMatricula());
        entity.setGenero(funcionario.getGenero());
        entity.setDataNascimento(funcionario.getDataNascimento());
        entity.setSetor(funcionario.getSetor());
        entity.setCargo(funcionario.getCargo());
        entity.setDataAdmissao(funcionario.getDataAdmissao());
        entity.setDataDemissao(funcionario.getDataDemissao());

        return repository.save(entity);
    }

    public void  delete(Long id){
        logger.info("Deleting one Funcionário");

        Funcionario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

}
