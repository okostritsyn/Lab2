package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.errors.AppError;
import nc.apps.model.Mark;
import nc.apps.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if(markService.delete((long) id)){
            return ResponseEntity.ok().build();
        }
        log.error("Error while deleting mark with id "+id);
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "mark with id " + id + " can not be deleted"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Mark mark) {
        if(markService.save(mark)){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving mark with id "+mark.getId());
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "mark with id " + mark.getId() + " can not be saved"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}