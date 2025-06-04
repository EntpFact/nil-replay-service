package com.hdfcbank.nilreplayservice.service;

import com.hdfcbank.nilreplayservice.model.MsgEventTracker;
import com.hdfcbank.nilreplayservice.repository.NilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplayService {

    @Autowired
    private NilRepository repository;

    @Autowired
    ReplayEventProcessor replayEventProcessor;

    public void replayFailedMessages() {
        int limit = 10;
        List<MsgEventTracker> batch;

        do {
            batch = repository.fetchReplayBatch("Failed",limit);
            for (MsgEventTracker event : batch) {

                replayEventProcessor.processFailedEvents(event);

                // ✅ Update as replayed
               repository.markAsReplayed(event.getMsgId(), event.getSource(), event.getTarget());
            }
        } while (!batch.isEmpty());
    }

    public void replayReceivedMessages() {
        int limit = 10;
        List<MsgEventTracker> batch;

        do {
            batch = repository.fetchReplayBatch("Received",limit);
            for (MsgEventTracker event : batch) {

                replayEventProcessor.processReceivedEvents(event);

                // ✅ Update as replayed
                repository.markAsReplayed(event.getMsgId(), event.getSource(), event.getTarget());
            }
        } while (!batch.isEmpty());
    }

}