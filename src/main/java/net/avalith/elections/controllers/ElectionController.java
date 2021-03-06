package net.avalith.elections.controllers;

import java.util.List;
import net.avalith.elections.entities.ElectionRequest;
import net.avalith.elections.entities.ElectionResponse;
import net.avalith.elections.entities.ElectionResultResponse;
import net.avalith.elections.entities.ResponseMessage;
import net.avalith.elections.entities.VoteRequest;
import net.avalith.elections.services.ElectionService;
import net.avalith.elections.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/election")
public class ElectionController {

  @Autowired
  private ElectionService electionService;

  @Autowired
  private VoteService voteService;

  @DeleteMapping(value = {"/{id}"})
  public void delete(@PathVariable Long id) {
    electionService.delete(id);
  }

  @GetMapping
  public List<ElectionResponse> getAll() {
    return electionService.getAll();
  }

  @PostMapping("")
  public void insert(@RequestBody @Validated ElectionRequest election) {
    electionService.insert(election);
  }

  @PostMapping(value = "/{idElection}/vote")
  public ResponseMessage vote(@PathVariable Long idElection,
      @RequestHeader("USER_ID") String userId,
      @RequestBody VoteRequest voteRequest) {

    return voteService.insert(voteRequest, userId, idElection);
  }

  @PutMapping
  public void modify(@RequestBody ElectionRequest election) {
    electionService.insert(election);
  }


  @ResponseStatus(value = HttpStatus.CONFLICT,
      reason = "Data integrity violation")  // 409
  @ExceptionHandler(DataIntegrityViolationException.class)
  public void conflict() {
    // Nothing to do
  }

  @GetMapping(value = "/{idElection}")
  public ElectionResultResponse getElectionResultResponse(@PathVariable Long idElection) {
    return electionService.getElectionResultResponse(idElection);
  }
}
