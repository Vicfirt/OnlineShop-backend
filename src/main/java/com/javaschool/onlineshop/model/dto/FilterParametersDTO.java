package com.javaschool.onlineshop.model.dto;

import java.util.List;

public class FilterParametersDTO {

    private List<String> brandsToFilter;

    private List<String> categoriesToFilter;

    public List<String> getBrandsToFilter() {
        return brandsToFilter;
    }

    public void setBrandsToFilter(List<String> brandsToFilter) {
        this.brandsToFilter = brandsToFilter;
    }

    public List<String> getCategoriesToFilter() {
        return categoriesToFilter;
    }

    public void setCategoriesToFilter(List<String> categoriesToFilter) {
        this.categoriesToFilter = categoriesToFilter;
    }
}
