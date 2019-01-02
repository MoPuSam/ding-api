package com.oulam.dingding.bean;

/**
 * 任务实体类
 */
public class Task {
    private String task_status;//任务状态
    private String create_time;//开始时间
    private String task_result;//结果
    private String userid;//任务处理人
    private String finish_time;//结束时间
    private String taskid;//任务节点id

    public Task(String task_status, String create_time, String task_result, String userid, String finish_time, String taskid) {
        this.task_status = task_status;
        this.create_time = create_time;
        this.task_result = task_result;
        this.userid = userid;
        this.finish_time = finish_time;
        this.taskid = taskid;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTask_result() {
        return task_result;
    }

    public void setTask_result(String task_result) {
        this.task_result = task_result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_status='" + task_status + '\'' +
                ", create_time='" + create_time + '\'' +
                ", task_result='" + task_result + '\'' +
                ", userid='" + userid + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", taskid='" + taskid + '\'' +
                '}';
    }
}
