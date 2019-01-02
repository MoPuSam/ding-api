package com.oulam.dingding.bean;

/**
 * 日志内容实体类
 */
public class ReportContent {
    private String sort;
    private String type;
    private String value;
    private String key;

    public ReportContent() {
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ReportContent{" +
                "sort='" + sort + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
