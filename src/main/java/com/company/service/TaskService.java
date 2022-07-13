package com.company.service;

import com.company.dto.TaskDTO;
import com.company.entity.TaskEntity;
import com.company.exc.ItemNotFoundException;
import com.company.repository.TaskRepository;
import jdk.jshell.execution.LoaderDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskDTO create(TaskDTO dto) {
        TaskEntity task = new TaskEntity();
        task.setContent(dto.getContent());
        task.setCreatedDate(LocalDateTime.now());

        taskRepository.save(task);
        dto.setId(task.getId());
        dto.setFinish(task.getFinish());
        dto.setCreatedDate(task.getCreatedDate());

        return dto;
    }

    public List<TaskDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        Page<TaskEntity> taskEntityList = taskRepository.findAll(pageable);

        List<TaskDTO> taskDTOList = new LinkedList<>();

        for (TaskEntity entity : taskEntityList) {
            taskDTOList.add(toDTO(entity));
        }
        return taskDTOList;
    }

    public TaskDTO update(String id, TaskDTO dto) {
        TaskEntity entity = taskRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task not found"));

        entity.setContent(dto.getContent());
        entity.setUpdateDate(LocalDateTime.now());

        taskRepository.save(entity);

        return toDTO(entity);
    }

    public void getById(String id) {
        Optional<TaskEntity> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Task not found");
        }
    }

    public Boolean delete(String id) {
        Optional<TaskEntity> optional = taskRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
        return true;
    }

    public TaskDTO finishTask(String id) {
        TaskEntity entity = taskRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Task not found"));
        entity.setFinish(true);
        entity.setFinishedDate(LocalDateTime.now());

        taskRepository.save(entity);

        return toDTO(entity);
    }

        @Scheduled(cron = "0 0 0,1 * * *")
//    @Scheduled(fixedRate = 1000)
    public void deldeteColumn() {
        List<TaskEntity> entityList = taskRepository.findAll();
        for (TaskEntity entity : entityList){
            if (LocalDateTime.now().getDayOfYear() - entity.getCreatedDate().getDayOfYear() == 1){
                taskRepository.deleteById(entity.getId());
            }
        }
    }

//    public Boolean find(int n){
//        List<TaskEntity> entityList = taskRepository.findAll();
//        for (TaskEntity entity : entityList){
//            if (LocalDateTime.now().getHour() == 18 && LocalDateTime.now().getMinute() - entity.getCreatedDate().getMinute() == n){
//                taskRepository.deleteById(entity.getId());
//            }
//        }
//        return false;
//    }

    public TaskDTO toDTO(TaskEntity entity) {
        TaskDTO dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setFinish(entity.getFinish());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setFinishedDate(entity.getFinishedDate());

        return dto;
    }

}
