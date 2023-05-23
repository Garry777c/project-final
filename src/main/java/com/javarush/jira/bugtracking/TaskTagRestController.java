package com.javarush.jira.bugtracking;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/task", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TaskTagRestController {

    private final TaskService taskService;

    @PutMapping(value = "/add_tag/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void addTag(@PathVariable long id, @RequestParam String tag){
        taskService.addTag(id, tag);
    }
}