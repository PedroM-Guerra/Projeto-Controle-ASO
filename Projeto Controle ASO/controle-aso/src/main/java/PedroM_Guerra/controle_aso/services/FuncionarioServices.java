package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.controllers.FuncionarioController;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.exception.BadRequestException;
import PedroM_Guerra.controle_aso.exception.DataIntegrityViolationException;
import PedroM_Guerra.controle_aso.exception.RequiredObjectIsNullException;
import PedroM_Guerra.controle_aso.exception.ResourceNotFoundException;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.repository.AsoRepository;
import PedroM_Guerra.controle_aso.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FuncionarioServices {

    private Logger logger = Logger.getLogger(FuncionarioServices.class.getName());

    @Autowired
    FuncionarioRepository repository;

    @Autowired
    AsoRepository asoRepository;

    @Autowired
    PagedResourcesAssembler<FuncionarioDTO> assembler;

    public PagedModel<EntityModel<FuncionarioDTO>> findAll(Pageable pageable){
        logger.info("Finding all Funcionários");

        var funcionarios = repository.findAll(pageable);

        var funcionariosWithLinks = funcionarios.map(funcionario -> {
            var dto = parseObject(funcionario, FuncionarioDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        Link findAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FuncionarioController.class)
                        .findAll(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(funcionariosWithLinks, findAllLink);
    }

    public PagedModel<EntityModel<FuncionarioDTO>> findByEnabledTrue(Pageable pageable){
        logger.info("Finding all active Funcionários (enabled = true)");

        var funcionarios = repository.findFuncionariosByEnabledTrue(pageable);

        var funcionariosWithLinks = funcionarios.map(funcionario -> {
            var dto = parseObject(funcionario, FuncionarioDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(FuncionarioController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(funcionariosWithLinks, findAllLink);
    }

    public PagedModel<EntityModel<FuncionarioDTO>> findByName(String nome, Pageable pageable){
        logger.info("Finding Funcionários by Name!");

        var funcionarios = repository.FindFuncionariosByName(nome, pageable);

        var funcionariosWithLinks = funcionarios.map(funcionario -> {
            var dto = parseObject(funcionario, FuncionarioDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        Link findAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FuncionarioController.class)
                        .findAll(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(funcionariosWithLinks, findAllLink);
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

        // 1. Busca se já existe alguém com esse CPF no banco
        Optional<Funcionario> funcionarioComMesmoCpf = repository.findByCpf(funcionario.getCpf());

        if (repository.findByCpf(funcionario.getCpf()).isPresent()) {
            throw new DataIntegrityViolationException("Este CPF já está cadastrado para outro funcionário.");
        }

        logger.info("Creating one Funcionário");

        var entity = parseObject(funcionario, Funcionario.class);

        var dto = parseObject(repository.save(entity), FuncionarioDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public FuncionarioDTO update(FuncionarioDTO funcionario){

        if (funcionario == null) throw new RequiredObjectIsNullException();

        // 1. Busca se já existe alguém com esse CPF no banco
        Optional<Funcionario> funcionarioComMesmoCpf = repository.findByCpf(funcionario.getCpf());

        // 2. Se existir e não for o próprio funcionário que está sendo editado, barra!
        if (funcionarioComMesmoCpf.isPresent() && !funcionarioComMesmoCpf.get().getId().equals(funcionario.getId())) {
            throw new DataIntegrityViolationException("Este CPF já está cadastrado para outro funcionário.");
        }

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

    @Transactional
    public FuncionarioDTO disableFuncionario(Long id){
        logger.info("Disabling one Funcionário");

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.disableFuncionario(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, FuncionarioDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Funcionário");

        Funcionario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        if (asoRepository.existsByFuncionarioId(id)){
            throw new BadRequestException("Não é possível deletar um funcionário que possui ASOs cadastrados.");
        }
        repository.delete(entity);
    }

    public PagedModel<EntityModel<FuncionarioDTO>> findFuncionariosComAsoMaisRecenteVencido(Pageable pageable) {
        logger.info("Finding all active Funcionários with expired most recent ASO");

        LocalDate hoje = LocalDate.now();

        var funcionarios = repository.findFuncionariosComAsoMaisRecenteVencido(hoje, pageable);

        var funcionariosWithLinks = funcionarios.map(funcionario -> {
            var dto = parseObject(funcionario, FuncionarioDTO.class);
            addHateoasLinks(dto);
            return dto;
        });


        Link selfLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(FuncionarioController.class)
                                .listarComAsoVencido(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(funcionariosWithLinks, selfLink);
    }

    public PagedModel<EntityModel<FuncionarioDTO>> findFuncionariosComAsoPertoDeVencer(Pageable pageable) {
        logger.info("Finding all active Funcionários with ASO expiring soon");

        LocalDate amanha = LocalDate.now().plusDays(1);
        LocalDate dataLimite = LocalDate.now().plusDays(30); // Define o alerta para 30 dias

        var funcionarios = repository.findFuncionariosComAsoPertoDeVencer(amanha, dataLimite, pageable);

        var funcionariosWithLinks = funcionarios.map(funcionario -> {
            var dto = parseObject(funcionario, FuncionarioDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link selfLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(FuncionarioController.class)
                                .listarPertoDeVencer(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(funcionariosWithLinks, selfLink);
    }

    private void addHateoasLinks(FuncionarioDTO dto) {
        dto.add(linkTo(methodOn(FuncionarioController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(FuncionarioController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(FuncionarioController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(FuncionarioController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(FuncionarioController.class).disableFuncionario(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(FuncionarioController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
