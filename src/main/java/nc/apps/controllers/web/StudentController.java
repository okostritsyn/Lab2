package nc.apps.controllers.web;

import nc.apps.errors.ResourceNotFoundException;
import nc.apps.model.Student;
import nc.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/new")
    public ModelAndView add() {
        Student student = new Student();
        return new ModelAndView("edit_student", "student", student);
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") String idPath) {
        Student student = studentService.get(Long.parseLong(idPath));
       if (student == null) {
           throw new ResourceNotFoundException("Student with id "+idPath+" not found");
        }
       return new ModelAndView("edit_student", "student", student);
    }

}