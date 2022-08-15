package nc.apps.controllers.rest;

import nc.apps.model.Specialization;
import nc.apps.model.SpecializationNode;
import nc.apps.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spec")
public class SpecRestController {

    @Autowired
    private SpecService specService;

    @RequestMapping(value = "/findAll",method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Specialization> findAll() {
        return specService.listAll();
    }

    @RequestMapping(value = "/findAllAsTree",method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SpecializationNode> findAllAsTree() {
        return specService.listAllAsTree();
    }

    @GetMapping("/findById")
    public Specialization findById(@RequestParam int Id) {
        return specService.get((long) Id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        specService.delete((long) id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody Specialization spec) {
        specService.save(spec);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByName")
    public List<Specialization> findByName(@RequestParam String name) {
        return specService.search(name);
    }

}