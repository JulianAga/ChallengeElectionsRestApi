package net.avalith.elections.controllers;

import net.avalith.elections.models.Election;
import net.avalith.elections.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Election")
public class ElectionController {

    @Autowired
    ElectionService electionService;

    @GetMapping
    public List<Election> getAll(){
        return electionService.getAll();
    }

    @PostMapping
    public void insert(Election election){
        electionService.insert(election);
    }

    @DeleteMapping
    public void delete(Long id){
        electionService.delete(id);
    }

    @PutMapping
    public void modify(Election election){
        electionService.insert(election);
    }
}
