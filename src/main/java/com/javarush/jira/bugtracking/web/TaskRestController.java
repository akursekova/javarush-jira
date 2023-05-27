package com.javarush.jira.bugtracking.web;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.bugtracking.to.TaskTagsTo;
import com.javarush.jira.login.AuthUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = TaskRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "tasks")
public class TaskRestController extends AbstractTaskController {
    public static final String REST_URL = "/api/tasks";

    @Autowired
    UserBelongRepository userBelongRepository;


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTagsToTask(@Valid @RequestBody TaskTagsTo taskTagsTo) {
        super.update(taskTagsTo);
    }

    @PostMapping("/{taskId}/watchers")
    public ResponseEntity<UserBelong> subscribe(@PathVariable String taskId, @AuthenticationPrincipal AuthUser authUser) {
        UserBelong userBelong = new UserBelong(Long.valueOf(taskId), ObjectType.TASK, authUser.id(), "admin"); //todo to add logic how to get user_type_code by user_id
        UserBelong created = userBelongRepository.save(userBelong);

        return ResponseEntity.ok().body(created);
    }

    @DeleteMapping("/{taskId}/watchers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribe(@PathVariable String taskId, @AuthenticationPrincipal AuthUser authUser) {
        Long userBelongId = userBelongRepository.getUserBelongByUserIdAndObjectIdAndObjectType(authUser.id(), Long.valueOf(taskId), ObjectType.TASK.ordinal(), "admin");
        userBelongRepository.delete(userBelongId);
    }

}
