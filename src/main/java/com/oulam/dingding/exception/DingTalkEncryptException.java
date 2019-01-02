package com.oulam.dingding.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉相关的异常
 */
public class DingTalkEncryptException extends Exception {
    public static final int SUCCESS = 0;
    public static final int ENCRYPTION_PLAINTEXT_ILLEGAL = 900001;
    public static final int ENCRYPTION_TIMESTAMP_ILLEGAL = 900002;
    public static final int ENCRYPTION_NONCE_ILLEGAL = 900003;
    public static final int AES_KEY_ILLEGAL = 900004;
    public static final int SIGNATURE_NOT_MATCH = 900005;
    public static final int COMPUTE_SIGNATURE_ERROR = 900006;
    public static final int COMPUTE_ENCRYPT_TEXT_ERROR = 900007;
    public static final int COMPUTE_DECRYPT_TEXT_ERROR = 900008;
    public static final int COMPUTE_DECRYPT_TEXT_LENGTH_ERROR = 900009;
    public static final int COMPUTE_DECRYPT_TEXT_CORPID_ERROR = 900010;
    private static Map<Integer, String> msgMap = new HashMap();
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public DingTalkEncryptException(Integer exceptionCode) {
        super((String)msgMap.get(exceptionCode));
        this.code = exceptionCode;
    }

    static {
        msgMap.put(Integer.valueOf(0), "成功");
        msgMap.put(Integer.valueOf(900001), "加密明文文本非法");
        msgMap.put(Integer.valueOf(900002), "加密时间戳参数非法");
        msgMap.put(Integer.valueOf(900003), "加密随机字符串参数非法");
        msgMap.put(Integer.valueOf(900005), "签名不匹配");
        msgMap.put(Integer.valueOf(900006), "签名计算失败");
        msgMap.put(Integer.valueOf(900004), "不合法的aes key");
        msgMap.put(Integer.valueOf(900007), "计算加密文字错误");
        msgMap.put(Integer.valueOf(900008), "计算解密文字错误");
        msgMap.put(Integer.valueOf(900009), "计算解密文字长度不匹配");
        msgMap.put(Integer.valueOf(900010), "计算解密文字corpid不匹配");
    }
}
