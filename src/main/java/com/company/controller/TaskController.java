package com.company.controller;

import com.company.dto.TaskDTO;
import com.company.entity.TaskEntity;
import com.company.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TaskDTO dto){
        return ResponseEntity.ok(taskService.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size",defaultValue = "10") int size){
        return ResponseEntity.ok(taskService.getList(page, size));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody TaskDTO dto){
        return ResponseEntity.ok(taskService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(taskService.delete(id));
    }

    @PutMapping("/finishTask/{id}")
    public ResponseEntity<?> finishTask(@PathVariable("id") String id){
        return ResponseEntity.ok(taskService.finishTask(id));
    }

//    @GetMapping("/find/{n}")
//    public ResponseEntity<?> find(@PathVariable("n") int n){
//        return ResponseEntity.ok(taskService.find(n));
//    }

}
