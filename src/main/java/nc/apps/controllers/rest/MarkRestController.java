package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.model.Mark;
import nc.apps.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@Log4j
public class MarkRestController {

    @Autowired
    private MarkService markService;

    @GetMapping(value = "/findByStudent/{studentId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Mark> findByStudent(@PathVariable int studentId) {
        return markService.listByStudent((long) studentId);
    }

    @GetMapping("/findById")
    public Mark findById(@RequestParam int Id) {
        return markService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        boolean status =  markService.delete((long) id);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while deleting mark");
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Mark mark) {
        boolean status = markService.save(mark);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving mark");
        return ResponseEntity.internalServerError().build();
    }
}