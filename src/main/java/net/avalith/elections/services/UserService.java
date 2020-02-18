package net.avalith.elections.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.avalith.elections.entities.FakeUsers;
import net.avalith.elections.entities.ResponseMessage;
import net.avalith.elections.models.User;
import net.avalith.elections.repositories.FakeUserDao;
import net.avalith.elections.repositories.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import retrofit2.Response;


@Service
public class UserService {

  @Autowired
  IUserDao userRepository;

  @Autowired
  private FakeUserDao fakeUsers;

  public void delete(Long id) {
    userRepository.deleteById(id);
  }


  public User findOne(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new
        ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct user Id"));
  }


  public List<User> getAll() {
    return userRepository.findAll();
  }

  /**
   * User insert method. Insert and save an user
   *
   * @param user Insert an user to be search
   */

  public void insert(User user) {
    if (user.getAge() == null) {
      user.setAge(calculateAge(user.getDayOfBirth()));
    }
    if (user.getId() == null) {
      user.setId(UUID.randomUUID().toString());
      user.setIsFake(false);
    }
    try {
      userRepository.save(user);
    } catch (ResponseStatusException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid user", ex);
    }
  }

  public Integer calculateAge(LocalDate startDate) {
    Period p = Period.between(startDate, LocalDate.now());
    return p.getYears();
  }

  /**Insert fake users.
   * Insert an amount of fake users
   * @param quantity amount of users to create
   * @return success message
   * @throws IOException for get users
   */
  public ResponseMessage insertFakeUsers(Long quantity) throws IOException {

    if (quantity < 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Error, insert a valid quantity of users");
    }

    Call<FakeUsers> allUsers = fakeUsers.getFakeUsers(quantity);
    Response<FakeUsers> execute = allUsers.execute();
    FakeUsers fakeUsersList = execute.body();

    assert fakeUsersList != null;
    List<User> users = fakeUsersList.getFakeUserList()
        .stream()
        .map(user ->
            User.builder()
                .firstName(user.getFakeUserName().getFirst())
                .lastName(user.getFakeUserName().getLast())
                .age(user.getFakeUserDob().getAge())
                .dayOfBirth(ZonedDateTime.parse(user.getFakeUserDob().getDate()).toLocalDate())
                .email(user.getEmail())
                .id(user.getFakeUserLogin().getUuid())
                .isFake(true)
                .build())
        .collect(Collectors.toList());

    AtomicInteger i = new AtomicInteger();

    users.forEach(user -> {
          if (Pattern.matches("^[A-Za-z0-9_.]+$", user.getFirstName())) {
            this.insert(user);
            i.getAndIncrement();
          }
        }
    );
    return new ResponseMessage(i + " usuarios de " + quantity + " creados correctamente");
  }
}
