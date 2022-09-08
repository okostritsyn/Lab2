package nc.apps.controllers.web;

import nc.apps.model.Subject;
import nc.apps.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping(path = "/viewAll")
    public ModelAndView viewAll() {
        return new ModelAndView("view_sub");
    }

    @GetMapping(path = "/new")
    public ModelAndView add() {
        Subject subject = new Subject();
        return new ModelAndView("edit_subject", "subject", subject);
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") String idPath) {
        Subject subject = subjectService.get(Long.parseLong(idPath));
       return new ModelAndView("edit_subject", "subject", subject);
    }

}