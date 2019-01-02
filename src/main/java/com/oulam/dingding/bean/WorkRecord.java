package com.oulam.dingding.bean;

import java.util.List;

/**
 * Created by Oulam on 2018/12/21.
 */
public class WorkRecord {
    private String record_id;
    private long create_time;
    private String title;
    private String url;
    private List<FormItem> form;

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FormItem> getForm() {
        return form;
    }

    public void setForm(List<FormItem> form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "WorkRecord{" +
                "record_id='" + record_id + '\'' +
                ", create_time=" + create_time +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", form=" + form +
                '}';
    }
}
