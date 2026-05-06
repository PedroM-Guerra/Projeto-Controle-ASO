package PedroM_Guerra.controle_aso.controllers;

import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.services.FuncionarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Funcionario> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario create(@RequestBody Funcionario funcionario){
        return service.create(funcionario);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario update(@RequestBody Funcionario funcionario){
        return service.create(funcionario);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
