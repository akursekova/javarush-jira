package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TaskRepository extends BaseRepository<Task> {
    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.sprint LEFT JOIN FETCH t.activities")
    List<Task> getAll();

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.sprint s LEFT JOIN FETCH t.activities WHERE s IS NOT NULL")
    List<Task> getAllWithNonEmptySprint();

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.sprint s WHERE s IS NULL")
    List<Task> getAllWithEmptySprint();
}
