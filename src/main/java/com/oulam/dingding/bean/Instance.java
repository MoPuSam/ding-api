package com.oulam.dingding.bean;

import java.util.Arrays;
import java.util.List;

/**
 * 审批实例类
 * @param :审批id,审批实例标题title,开始时间create_time,结束时间finish_time,发起人originator_userid
 * 发起部门originator_dept_id,发起部门名称originator_dept_name,表单详情列表form_component_values
 * 操作记录列表operation_records,审批结果result分为 agree 和 refuse,审批状态status,任务列表tasks
 */
public class Instance {
    private String id;
    private String title;
    private String create_time;
    private String finish_time;
    private String originator_userid;
    private String originator_dept_id;
    private String originator_dept_name;
    private String[] form_component_values;
    private String[] operation_records;
    private String result;
    private String status;
    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getOriginator_userid() {
        return originator_userid;
    }

    public void setOriginator_userid(String originator_userid) {
        this.originator_userid = originator_userid;
    }

    public String getOriginator_dept_id() {
        return originator_dept_id;
    }

    public void setOriginator_dept_id(String originator_dept_id) {
        this.originator_dept_id = originator_dept_id;
    }

    public String getOriginator_dept_name() {
        return originator_dept_name;
    }

    public void setOriginator_dept_name(String originator_dept_name) {
        this.originator_dept_name = originator_dept_name;
    }

    public String[] getForm_component_values() {
        return form_component_values;
    }

    public void setForm_component_values(String[] form_component_values) {
        this.form_component_values = form_component_values;
    }

    public String[] getOperation_records() {
        return operation_records;
    }

    public void setOperation_records(String[] operation_records) {
        this.operation_records = operation_records;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", create_time='" + create_time + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", originator_userid='" + originator_userid + '\'' +
                ", originator_dept_id='" + originator_dept_id + '\'' +
                ", originator_dept_name='" + originator_dept_name + '\'' +
                ", form_component_values=" + Arrays.toString(form_component_values) +
                ", operation_records=" + Arrays.toString(operation_records) +
                ", result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
