package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/task", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TaskTagRestController {

    private final TaskService taskService;

    @PostMapping(value = "/{id}/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void addTag(@PathVariable("id") long id, @RequestParam("tags") Set<String> tags){
        taskService.addTagToTask(id, tags);
    }

    @PostMapping(value = "/{taskId}/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBelong> subscribeUserToTask(@PathVariable("taskId") long taskId, @PathVariable("userId") long userId) {
        return new ResponseEntity<>(taskService.doTaskToUserBelong(taskId, userId), HttpStatus.OK);
    }

}