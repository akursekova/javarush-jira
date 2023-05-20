package com.javarush.jira.bugtracking.to;

import com.javarush.jira.common.to.BaseTo;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class TaskTagsTo extends BaseTo {

    Set<String> tags;


    public TaskTagsTo(Long id, Set<String> tags) {
        super(id);
        this.tags = tags;
    }
}
