package net.avalith.elections.controllers;

import net.avalith.elections.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/non-alcoholic-beer")
public class FakeUserController {

  @Autowired
  private UserService userService;

  @PostMapping("")
  public void insert(@RequestBody Long quantity) {
    userService.insertFakeUsers(quantity);
  }

}
