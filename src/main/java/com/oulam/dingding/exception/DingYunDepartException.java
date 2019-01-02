package com.oulam.dingding.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉部门返回码
 */
public class DingYunDepartException extends DingYunException{//部门相关返回码异常
    public static final int SUCCESS = 0;
    public static final int USER_ID_HAS_EXIST = 60001;
    public static final int USER_PHONE_ILLEGAL = 60002;
    public static final int USER_PHONE_HAS_EXIST = 60003;
    public static final int USER_EMAIL_ILLEGAL = 60004;
    public static final int USER_EMAIL_HAS_EXIST = 60005;
    public static final int USER_PHONE_HAS_LOGIN = 60006;
    public static final int DEPART_TOO_MUTCH = 60007;
    public static final int USER_ID_NOT_EXIST = 60008;
    public static final int USER_NAME_ILLEGAL = 60009;
    public static final int USER_INFO_NOT_NULL = 60010;
    private static Map<Integer, String> msgMap = new HashMap();
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public DingYunDepartException(Integer exceptionCode) {
        super((String)msgMap.get(exceptionCode));
        this.code = exceptionCode;
    }

    static {
        msgMap.put(Integer.valueOf(0), "成功");
        msgMap.put(Integer.valueOf(60001), "不合法的部门名称");
        msgMap.put(Integer.valueOf(60002), "部门层级深度超过限制");
        msgMap.put(Integer.valueOf(60003), "部门不存在");
        msgMap.put(Integer.valueOf(60004), "父亲部门不存在");
        msgMap.put(Integer.valueOf(60005), "不允许删除有成员的部门");
        msgMap.put(Integer.valueOf(60006), "不允许删除有子部门的部门");
        msgMap.put(Integer.valueOf(60007), "不允许删除根部门");
        msgMap.put(Integer.valueOf(60008), "父部门下该部门名称已存在");
        msgMap.put(Integer.valueOf(60009), "部门名称含有非法字符");
        msgMap.put(Integer.valueOf(60010), "部门存在循环关系");
    }
}
