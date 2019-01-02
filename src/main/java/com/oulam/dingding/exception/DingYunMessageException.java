package com.oulam.dingding.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉消息返回码
 */
public class DingYunMessageException extends DingYunException{
    public static final int SUCCESS = 0;
    public static final int USER_ID_ILLEGAL = 40003;
    public static final int MEDIA_TYPE_ILLEGAL = 40004;
    public static final int FILE_TYPE_ILLEGAL = 40005;
    public static final int FILE_SIZE_ILLEGAL = 40006;
    public static final int MEDIA_ID_ILLEGAL = 40007;
    public static final int MESSAGE_TYPE_ILLEGAL = 40008;
    public static final int SENDER_ILLEGAL = 40012;

    private static Map<Integer, String> msgMap = new HashMap();
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public DingYunMessageException(Integer exceptionCode) {
        super((String)msgMap.get(exceptionCode));
        this.code = exceptionCode;
    }

    static {
        msgMap.put(Integer.valueOf(0), "成功");
        msgMap.put(Integer.valueOf(40003), "不合法的UserID");
        msgMap.put(Integer.valueOf(40004), "不合法的媒体文件类型");
        msgMap.put(Integer.valueOf(40005), "不合法的文件类型");
        msgMap.put(Integer.valueOf(40006), "不合法的文件大小");
        msgMap.put(Integer.valueOf(40007), "不合法的媒体文件id");
        msgMap.put(Integer.valueOf(40008), "不合法的消息类型");
        msgMap.put(Integer.valueOf(40012), "不合法的发送者");
    }
}
