package com.lin.boke7qianduan.controller.param;

/**
 * @author lin
 */
public class AddCategoryParam {

    private String categoryName;
    private String description;

    @Override
    public String toString() {
        return "AddCategoryParam{" +
                "categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
