package com.company.repository;

import com.company.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {
    List<TaskEntity> findByCreatedDate(LocalDateTime createdDate);
}
