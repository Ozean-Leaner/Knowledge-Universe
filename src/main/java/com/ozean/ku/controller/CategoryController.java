package com.ozean.ku.controller;

import com.ozean.ku.entity.Category;
import com.ozean.ku.entity.Result;
import com.ozean.ku.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/home")
    public Result getCategories(HttpServletRequest request) {
        MDC.put("IP",request.getRemoteAddr());
        List<Category> categories = categoryService.getCategories();
        log.info("categories got");
        return new Result();
    }


}
