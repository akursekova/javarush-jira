package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository repository;
    private static final String IN_PROGRESS = "in progress";
    private static final String READY = "ready";
    private static final String DONE = "done";
    private static final String AND = " and ";

    public Optional<Duration> calculateDurationBetweenInProgressAndReady(Task task) {
        Optional<LocalDateTime> inProgressTimestamp = repository.findTaskTimestampByTaskAndStatus(task, IN_PROGRESS);
        Optional<LocalDateTime> readyTimestamp = repository.findTaskTimestampByTaskAndStatus(task, READY);


        if (inProgressTimestamp.isPresent() && readyTimestamp.isPresent()) {
            return Optional.of(Duration.between(inProgressTimestamp.get(), readyTimestamp.get()));
        } else {
            log.error(buildLogErrorMessage(task, inProgressTimestamp, readyTimestamp, IN_PROGRESS, READY));
            return Optional.empty();
        }
    }

    public Optional<Duration> calculateDurationBetweenReadyAndDone(Task task) {
        Optional<LocalDateTime> readyTimestamp = repository.findTaskTimestampByTaskAndStatus(task, READY);
        Optional<LocalDateTime> doneTimestamp = repository.findTaskTimestampByTaskAndStatus(task, DONE);


        if (readyTimestamp.isPresent() && doneTimestamp.isPresent()) {
            return Optional.of(Duration.between(readyTimestamp.get(), doneTimestamp.get()));
        } else {
            log.error(buildLogErrorMessage(task, readyTimestamp, doneTimestamp, READY, DONE));
            return Optional.empty();
        }
    }

    private String buildLogErrorMessage(Task task, Optional<LocalDateTime> startTimestamp, Optional<LocalDateTime> endTimestamp,
                                        String startStatus, String endStatus) {
        StringBuilder logMessage = new StringBuilder("Failed to calculate duration between '").append(startStatus)
                .append("'").append(AND).append("'").append(endStatus).append("' timestamps for the task ")
                .append(task).append(". There is no record that sets the task to ");


        if (startTimestamp.isEmpty() && endTimestamp.isEmpty()) {
            logMessage.append(startStatus).append(AND).append(endStatus);
        } else if (startTimestamp.isEmpty()) {
            logMessage.append(startStatus);
        } else {
            logMessage.append(endStatus);
        }

        logMessage.append(" status in Activity table.");
        return logMessage.toString();
    }

}
