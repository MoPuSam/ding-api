package com.oulam.dingding.bean;

import java.util.Date;

/**
 * 添加待入职入参
 */
public class PreEntryEmployeeAddParam {
    private String name;//员工姓名
    private String mobile;//手机号
    private Date pre_entry_time;//预期入职时间
    private String op_userid;//操作人userid
    private String extend_info;//扩展信息
    public PreEntryEmployeeAddParam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getPre_entry_time() {
        return pre_entry_time;
    }

    public void setPre_entry_time(Date pre_entry_time) {
        this.pre_entry_time = pre_entry_time;
    }

    public String getOp_userid() {
        return op_userid;
    }

    public void setOp_userid(String op_userid) {
        this.op_userid = op_userid;
    }

    public String getExtend_info() {
        return extend_info;
    }

    public void setExtend_info(String extend_info) {
        this.extend_info = extend_info;
    }
}
