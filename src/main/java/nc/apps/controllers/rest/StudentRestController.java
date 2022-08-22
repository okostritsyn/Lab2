package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.model.Student;
import nc.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Log4j
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> findAll() {
        return studentService.listAll();
    }

    @GetMapping("/findById")
    public Student findById(@RequestParam int Id) {
        return studentService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        boolean status = studentService.delete((long) id);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while deleting student");
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Student student) {
        boolean status = studentService.save(student);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving student");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/findByName")
    public List<Student> findByName(@RequestParam String name) {
        return studentService.search(name);
    }
}