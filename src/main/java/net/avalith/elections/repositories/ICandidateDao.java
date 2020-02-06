package net.avalith.elections.repositories;

import net.avalith.elections.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICandidateDao extends JpaRepository<Candidate,Long> {
}
