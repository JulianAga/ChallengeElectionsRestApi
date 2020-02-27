package net.avalith.elections.tasks;

import net.avalith.elections.services.ElectionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ElectionHistoryScheduledTask {

  @Autowired
  private ElectionHistoryService electionHistoryService;

  @Scheduled(cron = "${hour.totaldaytime}")
  public void actualizateHistory(){
    this.electionHistoryService.generateHistory();
  }
}
