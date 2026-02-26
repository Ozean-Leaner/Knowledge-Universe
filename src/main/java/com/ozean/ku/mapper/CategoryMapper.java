package com.ozean.ku.mapper;

import com.ozean.ku.entity.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {

    @Select("select * from category")
    List<Category> getCategories();
}
