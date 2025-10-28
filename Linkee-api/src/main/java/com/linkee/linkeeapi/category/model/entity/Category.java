package com.linkee.linkeeapi.category.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id" )
    private Long categoryId;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;
}
