package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 业务回调相关服务
 */
@Service
public class DingBizService {
    /**
     * 注册业务事件回调接口
     */
    public int registBizInterface(Map<String,Object> params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/call_back/register_call_back?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("call_back_tag", params.get("call_back_tag"));
        jsonObject.put("token", params.get("token"));
        jsonObject.put("aes_key", params.get("aes_key"));
        jsonObject.put("url", params.get("url"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        return errcode;
    }
    /**
     * 查询事件回调接口
     */
    public String getCallBack(){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/call_back/get_call_back?access_token=" + access_Token;
        String jsonstr = HttpClientUtil.doGet(dingDingurl, null);
        return jsonstr;
    }
    /**
     * 更新事件回调接口
     */
    public int updateCallBack(Map<String,Object> params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/call_back/update_call_back?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("call_back_tag", params.get("call_back_tag"));
        jsonObject.put("token", params.get("token"));
        jsonObject.put("aes_key", params.get("aes_key"));
        jsonObject.put("url", params.get("url"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        return errcode;
    }
    /**
     * 删除事件回调接口
     */
    public int deleteCallBack(){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/call_back/delete_call_back?access_token=" + access_Token;
        String jsonstr = HttpClientUtil.doGet(dingDingurl, null);
        JSONObject json = JSON.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        return errcode;
    }
    /**
     * 获取回调失败的结果
     */
    public String getCallBackFailedResult(){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/call_back/get_call_back_failed_result?access_token=" + access_Token;
        String jsonstr = HttpClientUtil.doGet(dingDingurl, null);
        return jsonstr;
    }
}
