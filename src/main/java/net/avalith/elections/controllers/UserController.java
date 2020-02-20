package net.avalith.elections.controllers;

import java.util.List;
import net.avalith.elections.models.User;
import net.avalith.elections.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping(value = "/user")
public class UserController {

  @Autowired
  private UserService userService;

  @DeleteMapping(value = {"/{id}"})
  public void delete(@PathVariable String id) {
    userService.delete(id);
  }

  @GetMapping
  public List<User> getAll() {
    return userService.getAll();
  }

  @PostMapping
  public void insert(@RequestBody User user) {
    userService.insert(user);
  }

  @PutMapping
  public void modify(@RequestBody User user) {
    userService.insert(user);
  }


  @ResponseStatus(value = HttpStatus.CONFLICT,
      reason = "Data integrity violation")  // 409
  @ExceptionHandler(DataIntegrityViolationException.class)
  public void conflict() {
    // Nothing to do
  }

  @Scheduled(cron = "0 * * * * ?")
  public void actualizateAge() {
    userService.reasignAge();
  }

}
