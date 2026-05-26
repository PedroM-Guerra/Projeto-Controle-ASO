package PedroM_Guerra.controle_aso.controllers;

import PedroM_Guerra.controle_aso.controllers.docs.AsoControllerDocs;
import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.enums.ResultadoAso;
import PedroM_Guerra.controle_aso.enums.TipoAso;
import PedroM_Guerra.controle_aso.services.AsoServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/aso/v1")
@Tag(name = "ASOs", description = "Endpoints for Managing ASOs")
public class AsoController implements AsoControllerDocs {

    @Autowired
    private AsoServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<PagedModel<EntityModel<AsoDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC: Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "dataEmissao"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/findAsoByFuncionarioId/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<PagedModel<EntityModel<AsoDTO>>> findAsosByFuncionarioId(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC: Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
        return ResponseEntity.ok(service.findAsosByFuncionarioId(id, pageable));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public AsoDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public AsoDTO create(@RequestBody AsoDTO aso){
        return service.create(aso);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public AsoDTO update(@RequestBody AsoDTO aso){
        return service.update(aso);
    }

    @PatchMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public AsoDTO disableAso(@PathVariable("id") Long id) {
        return service.disableAso(id);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipos")
    public List<Map<String, String>> getTiposAso() {
        return Arrays.stream(TipoAso.values()).map(t -> {
            Map<String, String> map = new HashMap<>();
            map.put("codigo", t.getCodigo());    // Vai gerar: "ADM", "PER", etc.
            map.put("descricao", t.getDescricao()); // Vai gerar: "Admissional", "Periódico", etc.
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/resultados")
    public List<Map<String, String>> getResultadosAso() {
        return Arrays.stream(ResultadoAso.values()).map(r -> {
            Map<String, String> map = new HashMap<>();
            map.put("codigo", r.getCodigo());    // Vai gerar: "A", "I"
            map.put("descricao", r.getDescricao()); // Vai gerar: "Apto", "Inapto"
            return map;
        }).collect(Collectors.toList());
    }
}
