package nc.apps.controllers.rest;

import lombok.extern.log4j.Log4j;
import nc.apps.errors.AppError;
import nc.apps.model.Group;
import nc.apps.model.Student;
import nc.apps.service.GroupService;
import nc.apps.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Log4j
public class GroupRestController {
    @Autowired
    private GroupService groupService;

    @GetMapping(value = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findAll() {
        return groupService.listAll();
    }

    @GetMapping("/findById")
    public Group findById(@RequestParam int Id) {
        return groupService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if (groupService.canBeDeleted((long) id)){
            if(groupService.delete((long) id)){
                return ResponseEntity.ok().build();
            }
        }
        log.error("Error while deleting group id "+id);

        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "group with id " + id + " can not be deleted"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Group group) {
        boolean status = groupService.save(group);
        if(status){
            return ResponseEntity.ok().build();
        }
        log.error("Error while saving group id "+group.getId());
        return new ResponseEntity(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "group with id " + group.getId() + " can not be saved"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/findByName")
    public List<Group> findByName(@RequestParam String name) {
        return groupService.search(name);
    }

}