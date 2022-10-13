package com.lin.boke7qianduan.controller.param;


/**
 * @author lin
 */
public class ListArticleYearMonthParam {
    private Integer year;
    private Integer month;

    @Override
    public String toString() {
        return "ListArticleYearMonthParam{" +
                "year=" + year +
                ", month=" + month +
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
}
