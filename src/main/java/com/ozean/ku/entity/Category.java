package com.ozean.ku.entity;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Category {
    @Length(max = 10)
    private String categoryName;
    @Id
    private Integer categoryId;
    @Length(max = 200)
    private String categoryDescription;
    private LocalDateTime categoryCreateTime;
    private LocalDateTime categoryUpdateTime;
    @NotEmpty
    private Integer categoryParentId;
    private Integer sort;
}
