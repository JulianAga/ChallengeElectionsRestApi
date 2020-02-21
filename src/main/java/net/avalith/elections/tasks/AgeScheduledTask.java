package net.avalith.elections.tasks;

import net.avalith.elections.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class AgeScheduledTask {

  @Autowired
  private UserService userService;

  @Scheduled(cron = "${day.totaltime}")
  public void actualizateAge() {
    userService.reasignAge();
  }

}
