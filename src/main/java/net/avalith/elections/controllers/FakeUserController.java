package net.avalith.elections.controllers;

import java.io.IOException;
import java.util.List;
import net.avalith.elections.entities.FakeUser;
import net.avalith.elections.entities.FakeUserRequest;
import net.avalith.elections.entities.ResponseMessage;
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
  public ResponseMessage insert(@RequestBody FakeUserRequest fakeUserRequest) throws IOException {
    return userService.insertFakeUsers(fakeUserRequest.getQuantity());
  }

}
