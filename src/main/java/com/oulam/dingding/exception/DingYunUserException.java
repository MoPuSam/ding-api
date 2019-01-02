package com.oulam.dingding.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉用户返回码
 */
public class DingYunUserException extends DingYunException{//用户返回码异常
    public static final int SUCCESS = 0;
    public static final int USER_ID_HAS_EXIST = 60102;
    public static final int USER_PHONE_ILLEGAL = 60103;
    public static final int USER_PHONE_HAS_EXIST = 60104;
    public static final int USER_EMAIL_ILLEGAL = 60105;
    public static final int USER_EMAIL_HAS_EXIST = 60106;
    public static final int USER_PHONE_HAS_LOGIN = 60107;
    public static final int DEPART_TOO_MUTCH = 60110;
    public static final int USER_ID_NOT_EXIST = 60111;
    public static final int USER_NAME_ILLEGAL = 60112;
    public static final int USER_INFO_NOT_NULL = 60113;
    public static final int USER_SEX_ILLEGAL = 60114;
    public static final int USER_INVID_NOT_USE = 60118;
    public static final int USER_POSITION_ILLEGAL = 60119;
    public static final int USER_HAS_BAN = 60120;
    public static final int USER_NOT_EXIST = 60121;
    private static Map<Integer, String> msgMap = new HashMap();
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public DingYunUserException(Integer exceptionCode) {
        super((String)msgMap.get(exceptionCode));
        this.code = exceptionCode;
    }

    static {
        msgMap.put(Integer.valueOf(0), "成功");
        msgMap.put(Integer.valueOf(60102), "UserID在公司中已存在");
        msgMap.put(Integer.valueOf(60103), "手机号码不合法");
        msgMap.put(Integer.valueOf(60104), "手机号码在公司中已存在");
        msgMap.put(Integer.valueOf(60105), "邮箱不合法");
        msgMap.put(Integer.valueOf(60106), "邮箱已存在");
        msgMap.put(Integer.valueOf(60107), "使用该手机登录钉钉的用户已经在企业中");
        msgMap.put(Integer.valueOf(60110), "部门个数超出限制");
        msgMap.put(Integer.valueOf(60111), "UserID不存在");
        msgMap.put(Integer.valueOf(60112), "用户name不合法");
        msgMap.put(Integer.valueOf(60113), "身份认证信息（手机/邮箱）不能同时为空");
        msgMap.put(Integer.valueOf(60114), "性别不合法");
        msgMap.put(Integer.valueOf(60118), "用户无有效邀请字段（邮箱，手机号）");
        msgMap.put(Integer.valueOf(60119), "不合法的position");
        msgMap.put(Integer.valueOf(60120), "用户已禁用");
        msgMap.put(Integer.valueOf(60121), "找不到该用户");
    }
}
