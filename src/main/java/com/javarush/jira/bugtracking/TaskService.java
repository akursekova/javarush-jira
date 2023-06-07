package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    public List<TaskTo> getAllWithNonEmptySprint() {
        return mapper.toToList(repository.getAllWithNonEmptySprint());
    }

    public List<TaskTo> getAllWithEmptySprint() {
        return mapper.toToList(repository.getAllWithEmptySprint());
    }

    public Page<TaskTo> getBacklogPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<TaskTo> tasksTo = getAllWithEmptySprint();
        List<TaskTo> list;

        if (tasksTo.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, tasksTo.size());
            list = tasksTo.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasksTo.size());
    }

}
