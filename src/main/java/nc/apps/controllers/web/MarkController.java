package nc.apps.controllers.web;

import nc.apps.model.Mark;
import nc.apps.service.MarkService;
import nc.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web/marks")
public class MarkController {
    @Autowired
    private MarkService markService;

    @Autowired
    private StudentService studentService;

    @GetMapping(path = "/viewmarks/{studentId}")
    public ModelAndView viewmarks(@PathVariable(value = "studentId") String studentId) {
        return new ModelAndView("view_marks","student", studentService.get(Long.parseLong(studentId)));
    }

    @GetMapping(path = "/new/{id}")
    public ModelAndView add(@PathVariable(value = "studentId") String studentId) {
        Mark mark = new Mark();
        mark.setStudentId(Long.parseLong(studentId));
        mark.setStudent(studentService.get(Long.parseLong(studentId)).getName());
        return new ModelAndView("edit_marks", "mark", mark);
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") String idPath) {
        Mark mark = markService.get(Long.parseLong(idPath));
        return new ModelAndView("edit_marks", "mark",mark);
    }

}