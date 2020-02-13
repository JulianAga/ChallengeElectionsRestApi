package net.avalith.elections.services;

import java.util.List;
import javax.transaction.Transactional;
import net.avalith.elections.models.CandidateByElection;
import net.avalith.elections.repositories.ICandidateByElectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CandidateByElectionService {

  @Autowired
  ICandidateByElectionDao candidateByElectionRepository;

  @Transactional
  public void delete(Long id) {
    candidateByElectionRepository.deleteById(id);
  }

  @Transactional
  public CandidateByElection findOne(Long id) {
    return candidateByElectionRepository.findById(id).orElseThrow(() -> new
        ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct election Id"));
  }

  public List<Long> find(List<Long> ids) {
    return find(ids);
  }

  @Transactional
  public List<CandidateByElection> getAll() {
    return candidateByElectionRepository.findAll();
  }

  /**
   * ElectionRegistry insert method. Insert and save a candidate
   *
   * @param candidateByElection Insert a candidate to be search
   */
  @Transactional
  public void insert(CandidateByElection candidateByElection) {
    this.candidateByElectionRepository.save(candidateByElection);
  }

}
