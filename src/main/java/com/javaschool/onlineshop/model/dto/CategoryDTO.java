package com.javaschool.onlineshop.model.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDTO {

    private Long categoryId;

    @Size(max = 100, message = "The number of characters should not exceed 100")
    @NotBlank(message = "Should not be empty")
    private String categoryName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
