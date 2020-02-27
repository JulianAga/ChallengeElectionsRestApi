package net.avalith.elections.repositories;

import java.util.List;
import net.avalith.elections.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query(
      value = "SELECT * FROM USERS u WHERE u.is_fake = 1",
      nativeQuery = true)
  List<User> findAllFakeUsers();

}
