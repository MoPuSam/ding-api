package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * 文件存储服务
 */
@Service
public class FileService {
    protected static final Logger log = LoggerFactory.getLogger(FileService.class);
    @Value("${com.oulam.microapp.agentId}")
    private String agentId;
    /**
     * 获取企业下的自定义空间
     */
    public String getCustomSpace(){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/cspace/get_custom_space?access_token=" + access_Token+"&domain=oulam";
        String jsonstr = HttpClientUtil.doGet(dingDingurl,null);
        log.info("企业下的自定义空间===================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        String spaceid = firstJson.getString("spaceid");
        return spaceid;
    }
    /**
     * 上传媒体文件返回media_id值
     * @param file
     * @param params
     * @return
     */
    public String uploadMedia(File file,Map<String, String> params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/media/upload?access_token=" + access_Token;
        String jsonstr = HttpClientUtil.sendFileAndParamsByHttpPost(file,dingDingurl,params);
        log.info("上传媒体文件返回值"+jsonstr);
        JSONObject json = JSON.parseObject(jsonstr);
        String media_id = json.getString("media_id");
        return media_id;
    }
    /**
     * 新增文件到用户钉盘
     * @param media_id
     * @return
     */
    public String addFileToDing(String media_id){
        return null;
    }
    /**
     * 发送钉盘文件给指定用户  agent_id不合法
     * @param userid
     * @param media_id
     * @param file_name
     * @return
     */
    public String sendFileToUser(String userid,String media_id,String file_name){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/cspace/add_to_single_chat?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",userid);
        jsonObject.put("media_id",media_id);
        jsonObject.put("file_name",file_name);
        jsonObject.put("agent_id",agentId);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        return jsonstr;
    }
}
