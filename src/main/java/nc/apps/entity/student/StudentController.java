package nc.apps.entity.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/test")
    public ModelAndView home() {
        List<Student> listStudents = studentService.listAll();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("listStudents", listStudents);
        return mav;
    }

    @RequestMapping("/")
    public ModelAndView home2() {
        List<Student> listStudents = studentService.listAll();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("listStudents", listStudents);
        return mav;
    }

    @RequestMapping( value = "/custom", method = RequestMethod.GET)
    public List<Student> home1() {
        List<Student> listStudents = studentService.listAll();
        return listStudents;
    }

    @RequestMapping("/new")
    public String newCustomerForm(Map<String, Object> model) {
        Student student = new Student();
        model.put("student", student);
        return "new_student";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCustomer(@ModelAttribute("student") Student student) {
        studentService.save(student);
        return "redirect:/";
    }

    @RequestMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("edit_student");
        Student student = studentService.get(id);
        mav.addObject("student", student);

        return mav;
    }

    @RequestMapping("/delete")
    public String deleteCustomerForm(@RequestParam long id) {
        studentService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String keyword) {
        List<Student> result = studentService.search(keyword);
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("result", result);

        return mav;
    }
}