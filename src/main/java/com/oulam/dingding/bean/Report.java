package com.oulam.dingding.bean;

import java.util.List;

/**
 * 用户日志实体类
 */
public class Report {
    private List<ReportContent> contents;
    private String remark;
    private String template_name;
    private String dept_name;
    private String creator_name;
    private String creator_id;
    private long create_time;
    private String report_id;

    public Report() {
    }

    public List<ReportContent> getContents() {
        return contents;
    }

    public void setContents(List<ReportContent> contents) {
        this.contents = contents;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    @Override
    public String toString() {
        return "Report{" +
                "contents=" + contents +
                ", remark='" + remark + '\'' +
                ", template_name='" + template_name + '\'' +
                ", dept_name='" + dept_name + '\'' +
                ", creator_name='" + creator_name + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", create_time=" + create_time +
                ", report_id='" + report_id + '\'' +
                '}';
    }
}
