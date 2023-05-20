package com.javarush.jira.bugtracking.internal.mapper;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.to.TaskTagsTo;
import com.javarush.jira.common.BaseMapper;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SprintMapper.class, ProjectMapper.class})
public interface TaskTagMapper extends BaseMapper<Task, TaskTagsTo> {

    @Override
    TaskTagsTo toTo(Task task);

    @Override
    Task toEntity(TaskTagsTo taskTagsTo);

    @Override
    List<TaskTagsTo> toToList(Collection<Task> tasks);
}
