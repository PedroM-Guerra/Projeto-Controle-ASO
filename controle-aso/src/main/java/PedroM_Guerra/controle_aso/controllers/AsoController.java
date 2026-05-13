package PedroM_Guerra.controle_aso.controllers;

import PedroM_Guerra.controle_aso.controllers.docs.AsoControllerDocs;
import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.services.AsoServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aso/v1")
@Tag(name = "ASOs", description = "Endpoints for Managing ASOs")
public class AsoController implements AsoControllerDocs {

    @Autowired
    private AsoServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<AsoDTO> findAll(){
        return service.findAll();
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

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
