package net.avalith.elections.services;

import java.util.List;
import net.avalith.elections.entities.ResponseMessage;
import net.avalith.elections.entities.VoteRequest;
import net.avalith.elections.models.CandidateByElection;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.User;
import net.avalith.elections.models.Vote;
import net.avalith.elections.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VoteService {

  @Autowired
  private VoteRepository voteRepository;

  @Autowired
  private CandidateByElectionService candidateByElectionService;

  @Autowired
  private UserService userService;

  @Autowired
  private ElectionService electionService;

  public void delete(Long id) {
    voteRepository.deleteById(id);
  }

  public Vote findById(Long id) {
    return voteRepository.findById(id).orElseThrow(() -> new
        ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct user Id"));
  }

  /**
   * insert method. Insert and save an user
   *
   * @param voteRequest Insert an vote
   */

  public ResponseMessage insert(VoteRequest voteRequest, String userId, Long electionId) {

    User user = userService.findById(userId);

    Election election = electionService.findOne(electionId);

    if (election == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Error, select a valid election");
    }

    CandidateByElection candidateByElection = getByCandidateAndElection(election,
        voteRequest.getCandidateId());

    Vote vote = Vote.builder()
        .candidateByElection(candidateByElection)
        .user(user)
        .build();

    if (userHasVoted(user.getVotes(), user, election)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already voted");
    } else {
      voteRepository.save(vote);
    }
    return new ResponseMessage("Vote successfully entered!");
  }

  /*** User has voted.
   *Verify if an user has already voted
   * @param votes Votes in database
   * @param user id of the user
   * @param election id of the election
   * @return Boolean true if user voted o
   */
  public Boolean userHasVoted(List<Vote> votes, User user, Election election) {

    return votes.stream().filter(
        vote -> vote.getUser().equals(user) && vote.getCandidateByElection()
            .getElection().equals(election)
    ).map(
        vote -> Boolean.TRUE
    ).findAny().orElse(Boolean.FALSE);

  }

  /*** Get candidate by election.
   * return candidate by election searching by the id_election and the id_candidate
   * @param election  election to search in
   * @param idCandidate id of the candidate to be search
   * @return Candidate by election
   */
  public CandidateByElection getByCandidateAndElection(Election election, Long idCandidate) {

    List<CandidateByElection> candidateByElections = election.getCandidateByElections();

    return candidateByElections.stream().filter(
        candidateByElection -> candidateByElection.getCandidate().getId().equals(idCandidate)
    ).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "The candidate does not exists!"));
  }
}
