package com.oulam.dingding.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉微应用相关返回码
 */
public class DingYunMicroAppException extends DingYunException{
    public static final int SUCCESS = 0;
    public static final int CORP_ID_ILLEGAL = 33001;
    public static final int MICRO_NAME_ILLEGAL = 33002;
    public static final int MICRO_DERES_ILLEGAL = 33003;
    public static final int MICRO_ICON_ILLEGAL = 33004;
    public static final int MICRO_MOBILE_ILLEGAL = 33005;
    public static final int MICRO_PC_ILLEGAL = 33006;
    public static final int MOBILE_PC_DIFFERENT = 33007;
    public static final int MICRO_OA_ILLEGAL = 33008;

    private static Map<Integer, String> msgMap = new HashMap();
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public DingYunMicroAppException(Integer exceptionCode) {
        super((String)msgMap.get(exceptionCode));
        this.code = exceptionCode;
    }

    static {
        msgMap.put(Integer.valueOf(0), "成功");
        msgMap.put(Integer.valueOf(33001), "无效的企业ID");
        msgMap.put(Integer.valueOf(33002), "无效的微应用的名称");
        msgMap.put(Integer.valueOf(33003), "无效的微应用的描述");
        msgMap.put(Integer.valueOf(33004), "无效的微应用的ICON");
        msgMap.put(Integer.valueOf(33005), "无效的微应用的移动端主页");
        msgMap.put(Integer.valueOf(33006), "无效的微应用的PC端主页");
        msgMap.put(Integer.valueOf(33007), "微应用的移动端的主页与PC端主页不同");
        msgMap.put(Integer.valueOf(33008), "无效的微应用OA后台的主页");
    }
}
