package com.oulam.dingding.bean;

/**
 * 表单组件
 */
public class FormComponent {
    private String name;
    private String value;
    private String ext_value;

    public FormComponent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExt_value() {
        return ext_value;
    }

    public void setExt_value(String ext_value) {
        this.ext_value = ext_value;
    }

    @Override
    public String toString() {
        return "FormComponent{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", ext_value='" + ext_value + '\'' +
                '}';
    }
}
