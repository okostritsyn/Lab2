package ok.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import ok.apps.errors.AppError;
import ok.apps.model.Student;
import ok.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (studentService.canBeDeleted((long) id)){
            if(studentService.delete((long) id)){
                return ResponseEntity.ok().build();
            }
        }
        log.error("Error while deleting student with id "+id);
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "student with id " + id + " can not be deleted"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Student student) {
        if(studentService.save(student)){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving student with id "+student.getId());
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "student with id " + student.getId() + " can not be saved"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByName")
    public List<Student> findByName(@RequestParam String name) {
        return studentService.search(name);
    }
}