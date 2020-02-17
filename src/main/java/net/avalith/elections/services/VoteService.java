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

  public void insert(VoteRequest voteRequest, String userId, Long electionId) {

    User user = userService.findOne(userId);

    CandidateByElection candidateByElection = getByCandidateAndElection(electionId,
        voteRequest.getCandidateId());

    if (candidateByElection == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Error, select a valid election");
    }

    Vote vote = Vote.builder()
        .candidateByElection(candidateByElection)
        .user(user)
        .build();

    if (!userHasVoted(user.getVotes(), userId, electionId)) {
      voteRepository.save(vote);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already voted");
    }
  }

  /*** User has voted.
   *Verify if an user has already voted
   * @param votes Votes in database
   * @param userId id of the user
   * @param electionId id of the election
   * @return Boolean true if user voted o
   */
  public Boolean userHasVoted(List<Vote> votes, String userId, Long electionId) {

    return votes.stream().filter(
        vote -> vote.getUser().getId().equals(userId) && vote.getCandidateByElection()
            .getElection().getId().equals(electionId)
    ).map(
        vote -> Boolean.TRUE
    ).findAny().orElse(Boolean.FALSE);

  }

  /*** Get candidate by election.
   * return candidate by election searching by the id_election and the id_candidate
   * @param idElection Id of the election to be search
   * @param idCandidate id of the candidate to be search
   * @return Candidate by election
   */
  public CandidateByElection getByCandidateAndElection(Long idElection, Long idCandidate) {

    List<CandidateByElection> candidateByElections = candidateByElectionService.getAll();

    List<CandidateByElection> candidateByElectionsFiltered = candidateByElections.stream()
        .filter(
            cbe -> cbe.getElection().getId().equals(idElection)
        ).collect(Collectors.toList());

    return candidateByElectionsFiltered.stream().filter(
        candidateByElection -> candidateByElection.getCandidate().getId().equals(idCandidate)
    ).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "The candidate does not exists!"));
  }
}
