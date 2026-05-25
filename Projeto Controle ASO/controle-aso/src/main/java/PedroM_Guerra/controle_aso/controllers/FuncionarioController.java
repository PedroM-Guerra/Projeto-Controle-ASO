package PedroM_Guerra.controle_aso.controllers;

import PedroM_Guerra.controle_aso.controllers.docs.FuncionarioControllerDocs;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.services.FuncionarioServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funcionario/v1")
@Tag(name = "Funcionarios", description = "Endpoints for Managing funcionarios")
public class FuncionarioController implements FuncionarioControllerDocs {

    @Autowired
    private FuncionarioServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<PagedModel<EntityModel<FuncionarioDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findByEnabledTrue(pageable));
    }

    @GetMapping(value = "/findFuncionarioByName/{nome}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<PagedModel<EntityModel<FuncionarioDTO>>> findByName(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findByName(nome, pageable));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public FuncionarioDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public FuncionarioDTO create(@RequestBody FuncionarioDTO funcionario){
        return service.create(funcionario);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public FuncionarioDTO update(@RequestBody FuncionarioDTO funcionario){
        return service.update(funcionario);
    }

    @PatchMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public FuncionarioDTO disableFuncionario(@PathVariable("id") Long id) {
        return service.disableFuncionario(id);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/generos")
    public List<Map<String, String>> getGeneros() {
        return Arrays.stream(GeneroFuncionario.values()).map(g -> {
            Map<String, String> map = new HashMap<>();
            map.put("codigo", g.getCodigo());          // M
            map.put("descricao", g.getDescricao());  // Masculino
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/setores")
    public List<Map<String, String>> getSetores() {
        return Arrays.stream(SetorFuncionario.values()).map(s -> {
            Map<String, String> map = new HashMap<>();
            map.put("codigo", s.getCodigo()); // ex: "TI", "RH"
            map.put("descricao", s.getDescricao());   // ex: "Tecnologia da Informação", "Recursos Humanos"
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/cargos")
    public List<Map<String, String>> getCargos() {
        return Arrays.stream(CargoFuncionario.values()).map(c -> {
            Map<String, String> map = new HashMap<>();
            map.put("codigo", c.getCodigo()); // ex: "DEV_JR", "GER_PROD"
            map.put("descricao", c.getDescricao());   // ex: "Desenvolvedor Júnior", "Gerente de Produto"
            return map;
        }).collect(Collectors.toList());
    }
}
