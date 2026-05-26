package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.controllers.AsoController;
import PedroM_Guerra.controle_aso.controllers.FuncionarioController;
import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.exception.ResourceNotFoundException;
import PedroM_Guerra.controle_aso.model.Aso;
import PedroM_Guerra.controle_aso.repository.AsoRepository;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    @Autowired
    PagedResourcesAssembler<AsoDTO> assembler;

    public PagedModel<EntityModel<AsoDTO>> findAll(Pageable pageable){
        logger.info("Finding all ASOs");

        var asos = repository.findAll(pageable);

        var asosWithLinks = asos.map(aso -> {
            var dto = parseObject(aso, AsoDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsoController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(asosWithLinks, findAllLink);
    }

    public PagedModel<EntityModel<AsoDTO>> findAsosByFuncionarioId(Long funcionarioId, Pageable pageable){
        logger.info("Finding ASOs by Funcionario Id");

        var asos = repository.findAsosByFuncionarioId(funcionarioId, pageable);

        var asosWithLinks = asos.map(aso -> {
            var dto = parseObject(aso, AsoDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsoController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(asosWithLinks, findAllLink);
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

        var funcionario = funcionarioRepository.findById(aso.getFuncionarioId())
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

        // AJUSTE AQUI: Buscar o funcionário novo caso o ID tenha mudado no update
        var funcionario = funcionarioRepository.findById(aso.getFuncionarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        entity.setFuncionario(funcionario);
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

    @Transactional
    public AsoDTO disableAso(Long id){
        logger.info("Disabling one Aso");

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.disableAso(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, AsoDTO.class);
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
        dto.add(linkTo(methodOn(AsoController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(AsoController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AsoController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AsoController.class).disableAso(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(AsoController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
