package PedroM_Guerra.controle_aso.controllers;

import PedroM_Guerra.controle_aso.controllers.docs.FuncionarioControllerDocs;
import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.services.FuncionarioServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario/v1")
@Tag(name = "Funcionarios", description = "Endpoints for Managing funcionarios")
public class FuncionarioController implements FuncionarioControllerDocs {

    @Autowired
    private FuncionarioServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<FuncionarioDTO> findAll(){
        return service.findAll();
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
        return service.disablePerson(id);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
