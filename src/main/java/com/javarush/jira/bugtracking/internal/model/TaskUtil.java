package com.javarush.jira.bugtracking.internal.model;

import com.javarush.jira.ref.RefType;
import com.javarush.jira.ref.ReferenceService;
import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class TaskUtil {

    public static void checkTagsExist(Collection<String> tags) {
        tags.forEach(tag -> ReferenceService.getRefTo(RefType.TAG, tag));
    }
}
