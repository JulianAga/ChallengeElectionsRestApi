package net.avalith.elections.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import net.avalith.elections.models.User;
import net.avalith.elections.repositories.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  @Autowired
  IUserDao userRepository;

  @Autowired
  RestTemplate restTemplate;

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
    user.setAge(calculateAge(user.getDayOfBirth()));
    user.setId(UUID.randomUUID().toString());
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

  public void insertFakeUsers(Long quantity) {
    String userApi = "https://randomuser.me/api/";

     List users = restTemplate.getForObject(userApi + "?results=" + quantity,List.class);

  }
}
