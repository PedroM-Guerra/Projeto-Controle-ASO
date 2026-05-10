package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.controllers.FuncionarioController;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.exception.ResourceNotFoundException;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseListObjects;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FuncionarioServices {

    private Logger logger = Logger.getLogger(FuncionarioServices.class.getName());

    @Autowired
    FuncionarioRepository repository;

    public List<FuncionarioDTO> findAll(){
        logger.info("Finding all Funcionários");

        var funcionarios =  parseListObjects(repository.findAll(), FuncionarioDTO.class);
        funcionarios.forEach(this::addHateoasLinks);
        return funcionarios;
    }

    public FuncionarioDTO findById(Long id){
        logger.info("Finding one Funcionário");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(entity, FuncionarioDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public FuncionarioDTO create(FuncionarioDTO funcionario){

        if (funcionario == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Funcionário");

        var entity = parseObject(funcionario, Funcionario.class);

        var dto = parseObject(repository.save(entity), FuncionarioDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public FuncionarioDTO update(FuncionarioDTO funcionario){

        if (funcionario == null) throw new RequiredObjectIsNullException();

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

        var dto = parseObject(repository.save(entity), FuncionarioDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Funcionário");

        Funcionario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    private void addHateoasLinks(FuncionarioDTO dto) {
        dto.add(linkTo(methodOn(FuncionarioController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(FuncionarioController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(FuncionarioController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(FuncionarioController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(FuncionarioController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
