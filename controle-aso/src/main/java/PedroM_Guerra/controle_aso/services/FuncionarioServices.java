package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.exception.ResourceNotFoundException;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseListObjects;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject;
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

    public List<FuncionarioDTO> findAll(){
        logger.info("Finding all Funcionários");

        return parseListObjects(repository.findAll(), FuncionarioDTO.class);
    }

    public FuncionarioDTO findById(Long id){
        logger.info("Finding one Funcionário");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return parseObject(entity, FuncionarioDTO.class);
    }

    public FuncionarioDTO create(FuncionarioDTO funcionario){
        logger.info("Creating one Funcionário");

        var entity = parseObject(funcionario, Funcionario.class);

        return parseObject(repository.save(entity), FuncionarioDTO.class);
    }

    public FuncionarioDTO update(FuncionarioDTO funcionario){
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

        return parseObject(repository.save(entity), FuncionarioDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one Funcionário");

        Funcionario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

}
