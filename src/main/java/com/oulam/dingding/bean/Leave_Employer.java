package com.oulam.dingding.bean;

import java.util.Date;
import java.util.List;

/**
 * 离职员工实体类
 */
public class Leave_Employer {
    private String userid;
    private Date last_work_day;
    private List<Department> dlist;
    private String reason_memo;
    private int reason_type;
    private int pre_status;
    private String handover_userid;
    private int status;
    private String main_dept_name;
    private int main_dept_id;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getLast_work_day() {
        return last_work_day;
    }

    public void setLast_work_day(Date last_work_day) {
        this.last_work_day = last_work_day;
    }

    public List<Department> getDlist() {
        return dlist;
    }

    public void setDlist(List<Department> dlist) {
        this.dlist = dlist;
    }

    public String getReason_memo() {
        return reason_memo;
    }

    public void setReason_memo(String reason_memo) {
        this.reason_memo = reason_memo;
    }

    public int getReason_type() {
        return reason_type;
    }

    public void setReason_type(int reason_type) {
        this.reason_type = reason_type;
    }

    public int getPre_status() {
        return pre_status;
    }

    public void setPre_status(int pre_status) {
        this.pre_status = pre_status;
    }

    public String getHandover_userid() {
        return handover_userid;
    }

    public void setHandover_userid(String handover_userid) {
        this.handover_userid = handover_userid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMain_dept_name() {
        return main_dept_name;
    }

    public void setMain_dept_name(String main_dept_name) {
        this.main_dept_name = main_dept_name;
    }

    public int getMain_dept_id() {
        return main_dept_id;
    }

    public void setMain_dept_id(int main_dept_id) {
        this.main_dept_id = main_dept_id;
    }

    @Override
    public String toString() {
        return "Leave_Employer{" +
                "userid='" + userid + '\'' +
                ", last_work_day=" + last_work_day +
                ", dlist=" + dlist +
                ", reason_memo='" + reason_memo + '\'' +
                ", reason_type=" + reason_type +
                ", pre_status=" + pre_status +
                ", handover_userid='" + handover_userid + '\'' +
                ", status=" + status +
                ", main_dept_name='" + main_dept_name + '\'' +
                ", main_dept_id=" + main_dept_id +
                '}';
    }
}
