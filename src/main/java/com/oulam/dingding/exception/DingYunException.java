package com.oulam.dingding.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oulam on 2018/12/31.
 */
public class DingYunException extends Exception{//钉钉全局相关错误返回码
    public static final int USER_EMAIL_HAS_EXIST = 60011;
    public static final int USER_PHONE_HAS_LOGIN = 60012;
    public static final int DEPART_TOO_MUTCH = 60013;
    public static final int USER_ID_NOT_EXIST = 60014;
    public static final int USER_NAME_ILLEGAL = 60015;
    private static Map<Integer, String> msgMap = new HashMap();
    private Integer code;

    public DingYunException(String message) {
        super(message);
    }

    public Integer getCode() {
        return this.code;
    }

    static {
        msgMap.put(Integer.valueOf(60011), "管理员权限不足，（user/department/agent）无权限");
        msgMap.put(Integer.valueOf(60012), "不允许删除默认应用");
        msgMap.put(Integer.valueOf(60013), "不允许关闭应用");
        msgMap.put(Integer.valueOf(60014), "不允许开启应用");
        msgMap.put(Integer.valueOf(60015), "不允许修改默认应用可见范围");
    }
}
