package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.MicroApp;
import com.oulam.dingding.exception.DingYunMicroAppException;
import com.oulam.dingding.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oulam on 2018/12/22.
 */
@Service
public class MicroAppService {
    /**
     * 微应用相关
     */
    //获取应用列表
    public List<MicroApp> getMicroApps(){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/microapp/list?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        JSONArray applist = json.getJSONArray("appList");
        List<MicroApp> malist = new ArrayList<>();
        for(int i = 0;i<applist.size();i++){
            MicroApp ma = new MicroApp();
            JSONObject app = applist.getJSONObject(i);
            ma.setAppIcon(app.getString("appIcon"));
            ma.setAgentId(app.getInteger("agentId"));
            ma.setSelf(app.getBoolean("isSelf"));
            ma.setName(app.getString("name"));
            ma.setHomepageLink(app.getString("homepageLink"));
            ma.setPcHomepageLink(app.getString("pcHomepageLink"));
            ma.setAppStatus(app.getInteger("appStatus"));
            ma.setOmpLink(app.getString("ompLink"));
            malist.add(ma);
        }
        return malist;
    }
    //创建应用
    public int addMicroApp(Map params) throws DingYunMicroAppException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/microapp/create?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appIcon", params.get("appIcon"));
        jsonObject.put("appName", params.get("appName"));
        jsonObject.put("appDesc", params.get("appDesc"));
        jsonObject.put("homepageUrl", params.get("homepageUrl"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject jobj = JSON.parseObject(jsonstr);
        int errcode = jobj.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMicroAppException(errcode);
        }
        int agentid = jobj.getInteger("agentid");
        return agentid;
    }
    //删除微应用
    public int delMicroApp(long agentId) throws DingYunMicroAppException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/microapp/delete?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agentId", agentId);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject jobj = JSON.parseObject(jsonstr);
        int errcode = jobj.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMicroAppException(errcode);
        }
        return errcode;
    }
    //更改微应用
    public int updateMicroApp(Map params) throws DingYunMicroAppException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/microapp/update?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        if(params.containsKey("appIcon")){
            jsonObject.put("appIcon", params.get("appIcon"));
        }
        if(params.containsKey("appName")){
            jsonObject.put("appName", params.get("appName"));
        }
        if(params.containsKey("appDesc")){
            jsonObject.put("appDesc", params.get("appDesc"));
        }
        if(params.containsKey("homepageUrl")){
            jsonObject.put("homepageUrl", params.get("homepageUrl"));
        }
        if(params.containsKey("pcHomepageUrl")){
            jsonObject.put("pcHomepageUrl", params.get("pcHomepageUrl"));
        }
        if(params.containsKey("ompLink")){
            jsonObject.put("ompLink", params.get("ompLink"));
        }
        if(params.containsKey("agentId")){
            jsonObject.put("agentId", params.get("agentId"));
        }
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject jobj = JSON.parseObject(jsonstr);
        int errcode = jobj.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMicroAppException(errcode);
        }
        int agentid = jobj.getInteger("agentid");
        return agentid;
    }
}
