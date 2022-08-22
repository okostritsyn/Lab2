package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.model.Subject;
import nc.apps.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@Log4j
public class SubjectRestController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Subject> findAll() {
        return subjectService.listAll();
    }

    @GetMapping("/findById")
    public Subject findById(@RequestParam int Id) {
        return subjectService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        boolean status = subjectService.delete((long) id);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while deleting subjects");
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Subject subject) {
        boolean status = subjectService.save(subject);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving subjects");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/findByName")
    public List<Subject> findByName(@RequestParam String name) {
        return subjectService.search(name);
    }
}