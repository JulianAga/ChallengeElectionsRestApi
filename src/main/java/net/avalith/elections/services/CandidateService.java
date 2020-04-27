package net.avalith.elections.services;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import net.avalith.elections.entities.CandidateResponse;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CandidateService {

  @Autowired
  private CandidateRepository candidateRepository;

  @Transactional
  public void delete(Long id) {
    candidateRepository.deleteById(id);
  }

  /***Find one.
   * find one candidate by his id
   * @param id identifier of the candidate
   * @return Candidate
   */
  public Candidate findOne(Long id) {
    return candidateRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Provide correct election Id"));
  }

  /***Get all the candidates.
   * get a list of all the candidates
   * @return List Response candidate
   */
  public List<CandidateResponse> getAll() {
    return candidateRepository.findAll().stream().map(
        candidate ->
            CandidateResponse.builder()
            .firstName(candidate.getFirstName())
            .lastName(candidate.getLastName())
            .id(candidate.getId())
            .build())
        .collect(Collectors.toList());
  }

  /**
   * Candidate insert method. Insert and save a candidate
   *
   * @param candidate Insert a candidate to be search
   */

  public void insert(Candidate candidate) {
    if (!candidateIsValid(candidate)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "error creating candidate, check the firstname and lastname");
    }
    this.candidateRepository.save(candidate);
  }

  /***Candidate is Valid.
   * Check if candidate has valid fields
   * @param candidate candidate to validate
   * @return
   */
  public boolean candidateIsValid(Candidate candidate) {

    boolean result = true;

    if (candidate.getFirstName().isEmpty()) {
      result = false;
    }

    if (candidate.getLastName().isEmpty()) {
      result = false;
    }

    if (candidate.getFirstName().length() < 2 || candidate.getFirstName().length() > 30) {
      result = false;
    }

    if (candidate.getLastName().length() < 2 || candidate.getLastName().length() > 30) {
      result = false;
    }

    return result;
  }

}
