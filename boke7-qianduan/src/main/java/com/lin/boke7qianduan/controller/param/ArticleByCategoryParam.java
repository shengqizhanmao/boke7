package com.lin.boke7qianduan.controller.param;

/**
 * @author lin
 */
public class ArticleByCategoryParam {
    private String radioCategory;
    private String radioDate;
    private String radioTags;
    private String radioViewSort;
    private Integer page;
    private Integer pagesize;

    @Override
    public String toString() {
        return "ArticleByCategoryParam{" +
                "radioCategory='" + radioCategory + '\'' +
                ", radioDate='" + radioDate + '\'' +
                ", radioTags='" + radioTags + '\'' +
                ", radioViewSort='" + radioViewSort + '\'' +
                ", page=" + page +
                ", pagesize=" + pagesize +
                '}';
    }

    public String getRadioTags() {
        return radioTags;
    }

    public void setRadioTags(String radioTags) {
        this.radioTags = radioTags;
    }

    public String getRadioCategory() {
        return radioCategory;
    }

    public void setRadioCategory(String radioCategory) {
        this.radioCategory = radioCategory;
    }

    public String getRadioDate() {
        return radioDate;
    }

    public void setRadioDate(String radioDate) {
        this.radioDate = radioDate;
    }


    public String getRadioViewSort() {
        return radioViewSort;
    }

    public void setRadioViewSort(String radioViewSort) {
        this.radioViewSort = radioViewSort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}
