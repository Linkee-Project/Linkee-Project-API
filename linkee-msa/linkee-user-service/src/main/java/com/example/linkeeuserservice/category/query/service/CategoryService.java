package com.example.linkeeuserservice.category.query.service;



import com.example.linkeeuserservice.category.query.dto.response.CategoryResponse;
import com.example.linkeeuserservice.category.query.mapper.CategoryMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAll() {
        return categoryMapper.findAll();
    }
}
