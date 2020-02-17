package net.avalith.elections.services;

import java.util.List;
import java.util.stream.Collectors;
import net.avalith.elections.entities.VoteRequest;
import net.avalith.elections.models.CandidateByElection;
import net.avalith.elections.models.User;
import net.avalith.elections.models.Vote;
import net.avalith.elections.repositories.IVoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VoteService {

  @Autowired
  IVoteDao voteRepository;

  @Autowired
  private CandidateByElectionService candidateByElectionService;

  @Autowired
  private UserService userService;

  @Autowired
  private ElectionService electionService;

  public void delete(Long id) {
    voteRepository.deleteById(id);
  }

  public Vote findOne(Long id) {
    return voteRepository.findById(id).orElseThrow(() -> new
        ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct user Id"));
  }

  public List<Vote> getAll() {
    return voteRepository.findAll();
  }

  /**
   * C insert method. Insert and save an user
   *
   * @param voteRequest Insert an vote
   */

  public void insert(VoteRequest voteRequest, String user_id, Long election_id) {

    User user = userService.findOne(user_id);

    CandidateByElection candidateByElection = getByCandidateAndElection(election_id,
        voteRequest.getCandidate_id());

    if (candidateByElection == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Error, select a valid election");
    }

    Vote vote = Vote.builder()
        .candidateByElection(candidateByElection)
        .user(user)
        .build();

    if (!userHasVoted(user.getVotes(), user_id, election_id)) {
      voteRepository.save(vote);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already voted");
    }
  }

  public Boolean userHasVoted(List<Vote> votes, String user_id, Long election_id) {

    return votes.stream().filter(
        vote -> vote.getUser().getId().equals(user_id) && vote.getCandidateByElection()
            .getElection().getId().equals(election_id)
    ).map(
        vote -> Boolean.TRUE
    ).findAny().orElse(Boolean.FALSE);

  }

  public CandidateByElection getByCandidateAndElection(Long id_election, Long id_candidate) {

    List<CandidateByElection> candidateByElections = candidateByElectionService.getAll();

    List<CandidateByElection> candidateByElectionsFiltered = candidateByElections.stream()
        .filter(
            cbe -> cbe.getElection().getId().equals(id_election)
        ).collect(Collectors.toList());

    return candidateByElectionsFiltered.stream().filter(
        candidateByElection -> candidateByElection.getCandidate().getId().equals(id_candidate)
    ).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "The candidate does not exists!"));

  }
}
