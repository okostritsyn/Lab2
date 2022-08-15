package nc.apps.controllers.rest;

import nc.apps.model.Group;
import nc.apps.model.Student;
import nc.apps.service.GroupService;
import nc.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/findAll",method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findAll() {
        return groupService.listAll();
    }

    @GetMapping("/findById")
    public Group findById(@RequestParam int Id) {
        return groupService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        groupService.delete((long) id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody Group group) {
        groupService.save(group);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByName")
    public List<Group> findByName(@RequestParam String name) {
        return groupService.search(name);
    }

}