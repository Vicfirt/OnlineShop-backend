package com.javaschool.onlineshop.model.dto;

public class StatisticsDTO {

    String category;

    Double salesSum;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(Double salesSum) {
        this.salesSum = salesSum;
    }

    public StatisticsDTO(String category, Double salesSum) {
        this.category = category;
        this.salesSum = salesSum;
    }
}
