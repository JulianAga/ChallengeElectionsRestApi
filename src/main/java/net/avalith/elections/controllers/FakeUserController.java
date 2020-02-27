package net.avalith.elections.controllers;

import java.io.IOException;
import net.avalith.elections.entities.FakeUserRequest;
import net.avalith.elections.entities.ResponseMessage;
import net.avalith.elections.entities.VoteRequest;
import net.avalith.elections.services.UserService;
import net.avalith.elections.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController

@RequestMapping("/non-alcoholic-beer")
public class FakeUserController {

  @Autowired
  private UserService userService;

  @Autowired
  private VoteService voteService;

  @PostMapping("")
  public ResponseMessage insert(@RequestBody FakeUserRequest fakeUserRequest) throws IOException {
    try {
      return userService.insertFakeUsers(fakeUserRequest.getQuantity());
    } catch (IOException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting users");
    }
  }

  @PostMapping(value = {"/{idElection}"})
  public void insertVotes(@PathVariable Long idElection,
      @RequestBody VoteRequest fakeVoteRequest) {
    voteService.voteCandidateWithFakeVotes(idElection, fakeVoteRequest);
  }

}
