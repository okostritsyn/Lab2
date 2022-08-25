package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.errors.AppError;
import nc.apps.model.Specialization;
import nc.apps.service.SpecService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spec")
@Log4j
public class SpecRestController {
    @Autowired
    private SpecService specService;

    @GetMapping(value = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Specialization> findAll() {
        return specService.listAll();
    }

    @GetMapping("/findById")
    public Specialization findById(@RequestParam int Id) {
        return specService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if (specService.canBeDeleted((long) id)){
            if(specService.delete((long) id)){
                return ResponseEntity.ok().build();
            }
        }
        log.error("Error while deleting spec with id "+id);
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "specialization with id " + id + " can not be deleted"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Specialization spec) {
        boolean status = specService.save(spec);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving spec with id "+spec.getId());
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "specialization with id " + spec.getId() + " can not be saved"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByName")
    public List<Specialization> findByName(@RequestParam String name) {
        return specService.search(name);
    }

}