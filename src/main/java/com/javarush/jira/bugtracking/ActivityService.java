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

    public long daysBetweenInProgressAndReady(Task task) {
        Optional<LocalDateTime> inProgressTimestamp = repository.findTaskDurationByTaskAndStatus(task, IN_PROGRESS);
        Optional<LocalDateTime> readyTimestamp = repository.findTaskDurationByTaskAndStatus(task, READY);


        if (inProgressTimestamp.isPresent() && readyTimestamp.isPresent()) {
            return Duration.between(inProgressTimestamp.get(), readyTimestamp.get()).toDaysPart();
        } else {
            log.error(buildLogErrorMessage(task, inProgressTimestamp, readyTimestamp, IN_PROGRESS, READY));
            return -1;
        }
    }

    public long daysBetweenReadyAndDone(Task task) {
        Optional<LocalDateTime> readyTimestamp = repository.findTaskDurationByTaskAndStatus(task, READY);
        Optional<LocalDateTime> doneTimestamp = repository.findTaskDurationByTaskAndStatus(task, DONE);


        if (readyTimestamp.isPresent() && doneTimestamp.isPresent()) {
            return Duration.between(readyTimestamp.get(), doneTimestamp.get()).toDaysPart();
        } else {
            log.error(buildLogErrorMessage(task, readyTimestamp, doneTimestamp, READY, DONE));
            return -1;
        }
    }

    private String buildLogErrorMessage(Task task, Optional<LocalDateTime> startTimestamp, Optional<LocalDateTime> endTimestamp,
                                        String startStatus, String endStatus) {
        StringBuilder logMessage = new StringBuilder("Failed to calculate duration between '").append(startStatus)
                .append("' and '").append(endStatus).append("' timestamps for the task ").append(task)
                .append(". There is no record that sets the task to ");


        if (!startTimestamp.isPresent() && !endTimestamp.isPresent()) {
            logMessage.append(startStatus).append(AND).append(endStatus);
        } else if (!startTimestamp.isPresent()) {
            logMessage.append(startStatus);
        } else {
            logMessage.append(endStatus);
        }

        logMessage.append(" status in Activity table.");
        return logMessage.toString();
    }

}
