package net.avalith.elections.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.avalith.elections.entities.ElectionRequest;
import net.avalith.elections.entities.ElectionResponse;
import net.avalith.elections.entities.ResponseCandidate;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.CandidateByElection;
import net.avalith.elections.models.Election;
import net.avalith.elections.repositories.IElectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ElectionService {

  @Autowired
  private IElectionDao electionRepository;

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private CandidateByElectionService candidateByElectionService;

  public void delete(Long id) {
    electionRepository.deleteById(id);
  }

  /***List Elections.
   * get all the elections
   * @return List ElectionResponse
   */
  public List<ElectionResponse> getAll() {
    return electionRepository.findAll().stream().map(
        election -> ElectionResponse.builder()
            .candidates(getResponseCandidateFromElection(election))
            .endDate(election.getEndDate())
            .startDate(election.getStartDate())
            .id(election.getId())
            .build()
    ).collect(Collectors.toList());
  }

  public Election findById(Long idElection) {
    return electionRepository.getOne(idElection);
  }

  private List<ResponseCandidate> getResponseCandidateFromElection(Election election) {

    return election.getCandidateByElections().stream().map(
        electionRegistry -> ResponseCandidate.builder()
            .firstName(electionRegistry.getCandidate().getFirstName())
            .lastName(electionRegistry.getCandidate().getLastName())
            .id(electionRegistry.getCandidate().getId())
            .build()
    ).collect(Collectors.toList());
  }

  /***Insert Election.
   *Insert an election
   * @param electionRequest election to insert
   */
  public void insert(@RequestBody ElectionRequest electionRequest) {

    LocalDateTime endDate = electionRequest.getEndDate();
    LocalDateTime startDate = electionRequest.getStartDate();

    if (!electionIsPosible(electionRequest)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "error creating election, check dates and candidates");
    }

    //Create a list with the candidates from the election and search them in candidate service
    List<Candidate> candidates = electionRequest.getCandidates()
        .stream()
        .map(c -> this.candidateService.findOne(c))
        .collect(Collectors.toList());

    //Build and save the election
    Election election = this.electionRepository.save(Election.builder()
        .endDate(electionRequest.getEndDate())
        .startDate(electionRequest.getStartDate())
        .candidateByElections(new ArrayList<>())
        .build());

    //Make a loop for candidates and create each candidate by election object
    candidates.forEach(c -> {
      candidateByElectionService.insert(
          CandidateByElection.builder()
              .election(election)
              .candidate(c)
              .build());
    });

  }

  private boolean electionIsPosible(ElectionRequest electionRequest) {
    boolean response = true;

    //Verify if end date is before the start date
    if (electionRequest.getEndDate().isBefore(electionRequest.getStartDate())) {
      response = false;
    }

    //Verify if end date is the same as start date
    if (electionRequest.getEndDate().isEqual(electionRequest.getStartDate())) {
      response = false;
    }

    //Verify if there are more than 2 candidates
    if (electionRequest.getCandidates().size() < 2) {
      response = false;
    }

    //Verify if election start date is before then now
    if (electionRequest.getStartDate().isBefore(LocalDateTime.now())) {
      response = false;
    }

    return response;
  }
}
