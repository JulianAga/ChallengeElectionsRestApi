package net.avalith.elections.repositories;

import net.avalith.elections.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User, String> {

}
