package com.example.course_work_phat_store.model.entities.stock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private Integer id;
    private String name;
}
