package com.company.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String id;
    private String content;
    private Boolean finish;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private LocalDateTime finishedDate;
}
