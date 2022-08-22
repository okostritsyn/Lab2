package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.model.Specialization;
import nc.apps.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
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
        boolean status = specService.delete((long) id);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while deleting spec");
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Specialization spec) {
        boolean status = specService.save(spec);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving spec");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/findByName")
    public List<Specialization> findByName(@RequestParam String name) {
        return specService.search(name);
    }

}