package net.avalith.elections.repositories;

import net.avalith.elections.models.CandidateByElection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateByElectionRepository extends JpaRepository<CandidateByElection,Long> {

}
