package net.avalith.elections.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.avalith.elections.entities.FakeUsers;
import net.avalith.elections.entities.ResponseMessage;
import net.avalith.elections.models.User;
import net.avalith.elections.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import retrofit2.Response;


@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RandomUserService fakeUsers;

  public void delete(String id) {
    userRepository.deleteById(id);
  }


  public User findById(String id) {
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

  public User insert(User user) {
    user.setAge(calculateAge(user.getDayOfBirth()));
    user.setId(UUID.randomUUID().toString());
    try {
      return userRepository.save(user);
    } catch (ResponseStatusException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a valid user", ex);
    }
  }

  public void update(User newUser, String id) {

    User oldUser = this.findById(id);
    oldUser.setFirstName(newUser.getFirstName());
    oldUser.setLastName(newUser.getLastName());
    oldUser.setAge(newUser.getAge());
    oldUser.setEmail(newUser.getEmail());
    oldUser.setDayOfBirth(newUser.getDayOfBirth());

    this.userRepository.save(oldUser);
  }

  public Integer calculateAge(LocalDate startDate) {
    Period p = Period.between(startDate, LocalDate.now());
    return p.getYears();
  }

  /**
   * Insert fake users. Insert an amount of fake users
   *
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
    Response<FakeUsers> response = allUsers.execute();
    FakeUsers fakeUsersList = Optional.ofNullable(response.body()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user cannot be null"));

    if (!response.isSuccessful()) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "error inserting fake users");
    } else {
      Long userQuantity = fakeUsersList.getFakeUserList()
          .stream()
          .map(user ->
              User.builder()
                  .firstName(user.getFakeUserName().getFirstName())
                  .lastName(user.getFakeUserName().getLastName())
                  .age(user.getFakeUserDob().getAge())
                  .dayOfBirth(ZonedDateTime.parse(user.getFakeUserDob().getDate()).toLocalDate())
                  .email(user.getEmail())
                  .id(user.getFakeUserLogin().getUuid())
                  .isFake(true)
                  .build())
          .filter(
              user -> Pattern.matches("^[A-Za-z0-9_.]+$", user.getFirstName())
          ).map(this::insert)
          .count();

      return new ResponseMessage(
          userQuantity + " usuarios de " + quantity + " creados correctamente");
    }
  }

  public List<User> getFakeUsers() {
    return userRepository.findAll().stream().filter(
        User::getIsFake
    ).collect(
            Collectors.toList());

  }
}
