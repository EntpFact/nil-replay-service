package com.hdfcbank.nilreplayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ReplayEventsScheduler {
    @Autowired
    private ReplayService replayService;

    @Scheduled(cron = "0 * 19 * * ?")
    public void scheduleTask()
    {
        replayService.replayFailedMessages();;
        replayService.replayReceivedMessages();
    }
}
