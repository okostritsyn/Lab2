package nc.apps.controllers.rest;

import nc.apps.model.Student;
import nc.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/findAll",method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> findAll() {
        return studentService.listAll();
    }

    @GetMapping("/findById")
    public Student findById(@RequestParam int Id) {
        return studentService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        studentService.delete((long) id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody Student student) {
        studentService.save(student);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByName")
    public List<Student> findByName(@RequestParam String name) {
        return studentService.search(name);
    }
}