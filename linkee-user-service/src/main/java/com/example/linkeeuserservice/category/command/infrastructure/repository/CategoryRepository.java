package com.example.linkeeuserservice.category.command.infrastructure.repository;

import com.example.linkeeuserservice.category.command.aggregate.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
