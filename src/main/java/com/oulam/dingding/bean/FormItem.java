package com.oulam.dingding.bean;

/**
 * 表单项.
 */
public class FormItem {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FormItem{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
