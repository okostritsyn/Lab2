package ok.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import ok.apps.errors.AppError;
import ok.apps.model.Subject;
import ok.apps.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (subjectService.canBeDeleted((long) id)){
            if(subjectService.delete((long) id)){
                return ResponseEntity.ok().build();
            }
        }
        log.error("Error while deleting subject with id "+id);
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "subject with id " + id + " can not be deleted"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Subject subject) {
        if(subjectService.save(subject)){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving subject with id "+subject.getId());
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "subject with id " + subject.getId() + " can not be saved"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByName")
    public List<Subject> findByName(@RequestParam String name) {
        return subjectService.search(name);
    }
}