package com.oulam.dingding.bean;

import java.util.Arrays;

/**
 * 外部联系人
 * └ title	职位
 └ share_dept_ids	共享部门ID列表
 └ label_ids	标签
 └ remark	备注
 └ address	地址
 └ name	姓名
 └ follower_user_id	负责人UserID
 └ state_code	国家码
 └ company_name	公司名
 └ share_user_ids	共享员工UserID列表
 └ mobile	手机号
 └ userid	外部联系人UserID
 */
public class ExtLinkMan {
    private String title;
    private int[] share_dept_ids;
    private int[] label_ids;
    private String remark;
    private String address;
    private String name;
    private String follower_user_id;
    private String state_code;
    private String company_name;
    private String[] share_user_ids;
    private String mobile;
    private String user_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getShare_dept_ids() {
        return share_dept_ids;
    }

    public void setShare_dept_ids(int[] share_dept_ids) {
        this.share_dept_ids = share_dept_ids;
    }

    public int[] getLabel_ids() {
        return label_ids;
    }

    public void setLabel_ids(int[] label_ids) {
        this.label_ids = label_ids;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFollower_user_id() {
        return follower_user_id;
    }

    public void setFollower_user_id(String follower_user_id) {
        this.follower_user_id = follower_user_id;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String[] getShare_user_ids() {
        return share_user_ids;
    }

    public void setShare_user_ids(String[] share_user_ids) {
        this.share_user_ids = share_user_ids;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ExtLinkMan{" +
                "title='" + title + '\'' +
                ", share_dept_ids=" + Arrays.toString(share_dept_ids) +
                ", label_ids=" + Arrays.toString(label_ids) +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", follower_user_id='" + follower_user_id + '\'' +
                ", state_code='" + state_code + '\'' +
                ", company_name='" + company_name + '\'' +
                ", share_user_ids=" + Arrays.toString(share_user_ids) +
                ", mobile='" + mobile + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
