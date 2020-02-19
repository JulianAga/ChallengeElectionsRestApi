package net.avalith.elections.controllers;

import java.io.IOException;
import java.util.List;
import net.avalith.elections.entities.FakeUser;
import net.avalith.elections.entities.FakeUserRequest;
import net.avalith.elections.entities.ResponseMessage;
import net.avalith.elections.services.UserService;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ImplementationDefinition.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController

@RequestMapping("/non-alcoholic-beer")
public class FakeUserController {

  @Autowired
  private UserService userService;

  @PostMapping("")
  public ResponseMessage insert(@RequestBody FakeUserRequest fakeUserRequest) throws IOException {
    try {
      return userService.insertFakeUsers(fakeUserRequest.getQuantity());
    }
    catch (IOException ex){
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error inserting users");
    }
  }

}
