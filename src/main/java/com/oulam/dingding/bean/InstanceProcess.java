package com.oulam.dingding.bean;

/**
 * 审批流实体类
 */
public class InstanceProcess {
    private String process_code;
    private String description;
    private String name;
    private String biz_category_id;
    private String form_content;

    public InstanceProcess() {
    }

    public String getProcess_code() {
        return process_code;
    }

    public void setProcess_code(String process_code) {
        this.process_code = process_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiz_category_id() {
        return biz_category_id;
    }

    public void setBiz_category_id(String biz_category_id) {
        this.biz_category_id = biz_category_id;
    }

    public String getForm_content() {
        return form_content;
    }

    public void setForm_content(String form_content) {
        this.form_content = form_content;
    }

    @Override
    public String toString() {
        return "InstanceProcess{" +
                "process_code='" + process_code + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", biz_category_id='" + biz_category_id + '\'' +
                ", form_content='" + form_content + '\'' +
                '}';
    }
}
