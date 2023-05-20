package com.javarush.jira.bugtracking.web;

import com.javarush.jira.bugtracking.internal.mapper.TaskTagMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.model.TaskUtil;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTagsTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractTaskController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected TaskRepository taskRepository;
    @Autowired
    private TaskTagMapper taskTagMapper;

    public void update(TaskTagsTo taskTagsTo) {
        log.info("update task with id {} with tags {}", taskTagsTo.id(), taskTagsTo);
        TaskUtil.checkTagsExist(taskTagsTo.getTags());
        Task task = taskTagMapper.updateFromTo(taskRepository.getExisted(taskTagsTo.id()), taskTagsTo);
        taskRepository.save(task);
    }
}
