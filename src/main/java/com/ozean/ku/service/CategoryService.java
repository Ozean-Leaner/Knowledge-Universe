package com.ozean.ku.service;

import com.ozean.ku.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
     List<Category>  getCategories();
}
