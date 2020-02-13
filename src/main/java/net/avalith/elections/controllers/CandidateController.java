package net.avalith.elections.controllers;

import java.util.List;
import net.avalith.elections.entities.ResponseCandidate;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @DeleteMapping(value = {"/{id}"})
  public void delete(@PathVariable Long id) {
    candidateService.delete(id);
  }

  @GetMapping
  public List<ResponseCandidate> getAll() {
    return candidateService.getAll();
  }

  @PostMapping
  public void insert(@RequestBody Candidate candidate) {
    candidateService.insert(candidate);
  }

  @PutMapping
  public void modify(@RequestBody Candidate candidate) {
    candidateService.insert(candidate);
  }

  @ResponseStatus(value = HttpStatus.CONFLICT,
      reason = "Data integrity violation")  // 409
  @ExceptionHandler(DataIntegrityViolationException.class)
  public void conflict() {
    // Nothing to do
  }
}
