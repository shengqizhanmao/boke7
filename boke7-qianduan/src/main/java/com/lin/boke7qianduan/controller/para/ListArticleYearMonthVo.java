package com.lin.boke7qianduan.controller.para;

/**
 * @author lin
 */
public class ListArticleYearMonthVo {
    private Integer year;
    private Integer month;
    private Integer size;

    @Override
    public String toString() {
        return "ListArticleYearMonthVo{" +
                "year=" + year +
                ", month=" + month +
                ", size=" + size +
                '}';
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
