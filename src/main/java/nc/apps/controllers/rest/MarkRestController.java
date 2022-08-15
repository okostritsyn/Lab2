package nc.apps.controllers.rest;

import nc.apps.model.Mark;
import nc.apps.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
public class MarkRestController {

    @Autowired
    private MarkService markService;

    @RequestMapping(value = "/findByStudent/{studentId}",method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Mark> findByStudent(@PathVariable int studentId) {
        return markService.listByStudent((long) studentId);
    }

    @GetMapping("/findById")
    public Mark findById(@RequestParam int Id) {
        return markService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        markService.delete((long) id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody Mark mark) {
        markService.save(mark);
        return ResponseEntity.ok().build();
    }
}