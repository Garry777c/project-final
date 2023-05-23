package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    public void addTag(long taskId, String tag){
        Task task = repository.getExisted(taskId);
        Set <String> setOfTags = task.getTags();
        if(setOfTags.isEmpty() || setOfTags.contains(tag)) {
            validateTags(tag, 2, 32, setOfTags);
            repository.save(task);
        }
    }

    public void validateTags(String tag, int min, int max, Set<String> setOfTags){
        if(tag.length()>=min && tag.length()<=max) {
            setOfTags.add(tag);
        }
    }
}
