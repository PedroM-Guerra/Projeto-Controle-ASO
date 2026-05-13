package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.controllers.AsoController;
import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.exception.ResourceNotFoundException;
import PedroM_Guerra.controle_aso.model.Aso;
import PedroM_Guerra.controle_aso.repository.AsoRepository;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseListObjects;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AsoServices {

    private Logger logger = Logger.getLogger(AsoServices.class.getName());

    @Autowired
    AsoRepository repository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public List<AsoDTO> findAll(){
        logger.info("Finding all ASOs");

        var asos =  parseListObjects(repository.findAll(), AsoDTO.class);
        asos.forEach(this::addHateoasLinks);
        return asos;
    }

    public AsoDTO findById(Long id){
        logger.info("Finding one ASO");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(entity, AsoDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public AsoDTO create(AsoDTO aso){
        if (aso == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one ASO");

        var entity = parseObject(aso, Aso.class);

        var funcionario = funcionarioRepository.findById(aso.getFuncionario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        entity.setFuncionario(funcionario);

        if (entity.getDataEmissao() != null) {
            entity.setDataValidade(entity.getDataEmissao().plusYears(1));
        }

        var dto = parseObject(repository.save(entity), AsoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public AsoDTO update(AsoDTO aso){

        if (aso == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one ASO");

        Aso entity = repository.findById(aso.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFuncionario(aso.getFuncionario());
        entity.setCrmMedico(aso.getCrmMedico());
        entity.setNomeMedico(aso.getNomeMedico());
        entity.setDescricaoExame(aso.getDescricaoExame());
        entity.setUrlDocumentoScan(aso.getUrlDocumentoScan());
        entity.setDataEmissao(aso.getDataEmissao());
        entity.setDataValidade(aso.getDataValidade());
        entity.setTipoAso(aso.getTipoAso());
        entity.setResultadoAso(aso.getResultadoAso());

        var dto = parseObject(repository.save(entity), AsoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one ASO");

        Aso entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    private void addHateoasLinks(AsoDTO dto) {
        dto.add(linkTo(methodOn(AsoController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(AsoController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(AsoController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AsoController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AsoController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
