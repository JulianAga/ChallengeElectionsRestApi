package net.avalith.elections.repositories;

import net.avalith.elections.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IElectionDao extends JpaRepository<Election, Long> {

}
