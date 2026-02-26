package com.ozean.ku.service.impl;

import com.ozean.ku.entity.Category;
import com.ozean.ku.exception.CategoryException;
import com.ozean.ku.mapper.CategoryMapper;
import com.ozean.ku.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryMapper.getCategories();
        if (categories == null) {
            throw new CategoryException("获取类别列表失败!");
        }
        return categories;
    }
}
