package com.oulam.dingding.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 云表中的消息表实体类
 */
public class Y_Message {
    private BigDecimal latoid;

    private BigDecimal formid;

    private Double recorder;

    private BigDecimal latoversion;

    private Boolean latolocked;

    private String f1;

    private String f2; //推送用户

    private String f3; //推送部门

    private Boolean f4; //是否推送全员

    private String f5; //消息内容

    private String mCreatorAccount;

    private String mUpdaterAccount;

    private Long mCreatorId;

    private Date mCreateTime;

    private Long mUpdaterId;

    private Date mUpdateTime;

    private Long mProcessInstId;

    private Long mOrgId;

    private String f6;

    private String f7;

    private String f8;

    private Boolean f9; //推送是否完成

    public BigDecimal getLatoid() {
        return latoid;
    }

    public void setLatoid(BigDecimal latoid) {
        this.latoid = latoid;
    }

    public BigDecimal getFormid() {
        return formid;
    }

    public void setFormid(BigDecimal formid) {
        this.formid = formid;
    }

    public Double getRecorder() {
        return recorder;
    }

    public void setRecorder(Double recorder) {
        this.recorder = recorder;
    }

    public BigDecimal getLatoversion() {
        return latoversion;
    }

    public void setLatoversion(BigDecimal latoversion) {
        this.latoversion = latoversion;
    }

    public Boolean getLatolocked() {
        return latolocked;
    }

    public void setLatolocked(Boolean latolocked) {
        this.latolocked = latolocked;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1 == null ? null : f1.trim();
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2 == null ? null : f2.trim();
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3 == null ? null : f3.trim();
    }

    public Boolean getF4() {
        return f4;
    }

    public void setF4(Boolean f4) {
        this.f4 = f4;
    }

    public String getF5() {
        return f5;
    }

    public void setF5(String f5) {
        this.f5 = f5 == null ? null : f5.trim();
    }

    public String getmCreatorAccount() {
        return mCreatorAccount;
    }

    public void setmCreatorAccount(String mCreatorAccount) {
        this.mCreatorAccount = mCreatorAccount == null ? null : mCreatorAccount.trim();
    }

    public String getmUpdaterAccount() {
        return mUpdaterAccount;
    }

    public void setmUpdaterAccount(String mUpdaterAccount) {
        this.mUpdaterAccount = mUpdaterAccount == null ? null : mUpdaterAccount.trim();
    }

    public Long getmCreatorId() {
        return mCreatorId;
    }

    public void setmCreatorId(Long mCreatorId) {
        this.mCreatorId = mCreatorId;
    }

    public Date getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(Date mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public Long getmUpdaterId() {
        return mUpdaterId;
    }

    public void setmUpdaterId(Long mUpdaterId) {
        this.mUpdaterId = mUpdaterId;
    }

    public Date getmUpdateTime() {
        return mUpdateTime;
    }

    public void setmUpdateTime(Date mUpdateTime) {
        this.mUpdateTime = mUpdateTime;
    }

    public Long getmProcessInstId() {
        return mProcessInstId;
    }

    public void setmProcessInstId(Long mProcessInstId) {
        this.mProcessInstId = mProcessInstId;
    }

    public Long getmOrgId() {
        return mOrgId;
    }

    public void setmOrgId(Long mOrgId) {
        this.mOrgId = mOrgId;
    }

    public String getF6() {
        return f6;
    }

    public void setF6(String f6) {
        this.f6 = f6 == null ? null : f6.trim();
    }

    public String getF7() {
        return f7;
    }

    public void setF7(String f7) {
        this.f7 = f7 == null ? null : f7.trim();
    }

    public String getF8() {
        return f8;
    }

    public void setF8(String f8) {
        this.f8 = f8 == null ? null : f8.trim();
    }

    public Boolean getF9() {
        return f9;
    }

    public void setF9(Boolean f9) {
        this.f9 = f9;
    }
}