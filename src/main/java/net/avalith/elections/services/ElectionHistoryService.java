package net.avalith.elections.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.NoSuchElementException;
import net.avalith.elections.models.CandidateByElection;
import net.avalith.elections.models.ElectionHistory;
import net.avalith.elections.repositories.ElectionHistoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionHistoryService {

  @Autowired
  ElectionHistoryRespository electionHistoryRespository;

  @Autowired
  ElectionService electionService;

  @Autowired
  VoteService voteService;

  public void generateHistory() {

    this.electionService.getActiveElections().forEach(election -> {

      CandidateByElection candidateByElection = election.getCandidateByElections().stream()
          .max(Comparator.comparing(candidateByElections -> voteService
              .getVotesByCandidateByElection(candidateByElections.getId())))
          .orElseThrow(NoSuchElementException::new);

      this.save(ElectionHistory.builder()
          .partialWinner(candidateByElection.getId())
          .percentage(
              (double) (voteService.getVotesByElection(election.getId()) * 100 / voteService
                  .getVotesByCandidateByElection(candidateByElection.getId())))
          .votes(voteService.getVotesByCandidateByElection(candidateByElection.getId()))
          .createdAt(LocalDateTime.now())
          .build());
    });
  }

  public void save(ElectionHistory electionHistory){
    this.electionHistoryRespository.save(electionHistory);
  }

}





