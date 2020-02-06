package net.avalith.elections.controllers;

import net.avalith.elections.models.Candidate;
import net.avalith.elections.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Candidate")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @GetMapping
    public List<Candidate> getAll(){
        return candidateService.getAll();
    }

    @PostMapping
    public void insert(Candidate candidate){
        candidateService.insert(candidate);
    }

    @DeleteMapping
    public void delete(Long id){
        candidateService.delete(id);
    }

    @PutMapping
    public void modify(Candidate candidate){
        candidateService.insert(candidate);
    }

}
