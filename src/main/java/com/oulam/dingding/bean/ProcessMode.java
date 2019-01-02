package com.oulam.dingding.bean;

/**
 * 审批模板实体类
 */
public class ProcessMode {
    private String name;
    private  String icon_url;
    private String process_code;
    private String url;

    public ProcessMode() {
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

    public String getProcess_code() {
        return process_code;
    }

    public void setProcess_code(String process_code) {
        this.process_code = process_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", process_code='" + process_code + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
