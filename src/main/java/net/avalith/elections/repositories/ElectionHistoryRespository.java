package net.avalith.elections.repositories;

import net.avalith.elections.models.ElectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionHistoryRespository extends JpaRepository<ElectionHistory, Long> {

}
