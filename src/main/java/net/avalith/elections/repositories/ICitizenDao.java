package net.avalith.elections.repositories;

import net.avalith.elections.models.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitizenDao extends JpaRepository<Citizen,Long> {
}
