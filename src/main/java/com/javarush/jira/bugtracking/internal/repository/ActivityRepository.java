package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.Activity;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ActivityRepository extends BaseRepository<Activity> {
    @Query(value = "SELECT a.updated from Activity a WHERE a.task=:task and a.statusCode=:status")
    Optional<LocalDateTime> findTaskTimestampByTaskAndStatus(Task task, String status);
}
