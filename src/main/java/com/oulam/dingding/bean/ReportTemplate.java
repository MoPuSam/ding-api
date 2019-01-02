package com.oulam.dingding.bean;

/**
 * 日志模板实体类.
 */
public class ReportTemplate {
    private String name;
    private String icon_url;
    private String report_code;
    private String url;

    public ReportTemplate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getReport_code() {
        return report_code;
    }

    public void setReport_code(String report_code) {
        this.report_code = report_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ReportTemplate{" +
                "name='" + name + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", report_code='" + report_code + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
