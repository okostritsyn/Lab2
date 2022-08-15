package nc.apps.controllers.rest;

import nc.apps.model.Subject;
import nc.apps.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/findAll",method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Subject> findAll() {
        return subjectService.listAll();
    }

    @GetMapping("/findById")
    public Subject findById(@RequestParam int Id) {
        return subjectService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        subjectService.delete((long) id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody Subject subject) {
        subjectService.save(subject);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByName")
    public List<Subject> findByName(@RequestParam String name) {
        return subjectService.search(name);
    }
}