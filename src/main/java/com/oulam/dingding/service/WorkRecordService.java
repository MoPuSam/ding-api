package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.oulam.dingding.bean.FormItem;
import com.oulam.dingding.bean.WorkRecord;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 待办事项相关
 */
@Service
public class WorkRecordService {
    protected static final Logger log = LoggerFactory.getLogger(WorkRecordService.class);
    @Value("${oulam.erp.path}")
    private String url;

    /**
     * 发起待办
     * @param params
     * @param form
     * @return
     */
    public String addWorkRecord(Map<String,Object> params,List<FormItem> form){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/workrecord/add?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", params.get("userid"));
        jsonObject.put("create_time", params.get("create_time"));
        jsonObject.put("url", url);
        jsonObject.put("title", params.get("title"));

        jsonObject.put("formItemList", form);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        log.info("待办发起结果="+jsonstr);
        return jsonstr;
    }

    /**
     * 将发起的待办同步到云表
     * @param jsonstr
     * @param userid
     * @return
     */
    public int addWorkRecord(String jsonstr,String userid){
        JSONObject jsonObject = JSON.parseObject(jsonstr);
        String record_id = jsonObject.getString("record_id");

        return 0;
    }
    /**
     * 更新待办事项 调用成功后，该待办事项在该用户的“待办事项”列表页面中消失
     * @param userid
     * @param record_id
     * @return
     */
    public String updateWorkRecord(String userid,String record_id){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/workrecord/update?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",userid);
        jsonObject.put("record_id",record_id);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        return jsonstr;
    }
    /**
     * 获取用户待办事项
     * @param userid
     * @param offset
     * @param limit
     * @param status
     * @return
     */
    public String getWorkRecordByUid(String userid,int offset,int limit,int status){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/workrecord/getbyuserid?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",userid);
        jsonObject.put("offset",offset);
        jsonObject.put("limit",limit);
        jsonObject.put("status",status);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        log.info("id为"+userid+"的待办事项："+jsonstr);
        return jsonstr;
    }
    /**
     * 获取用户下的所有未完成待办
     * @param userid
     * @param wrlist
     * @param offset
     */
    public void getAllWorkRecordByUid(String userid, List<WorkRecord> wrlist,int offset){
        int limit = 50;
        //未完成
        int status = 0;
        String jsonstr = getWorkRecordByUid(userid,offset,limit,status);
        JSONObject firstObj = JSON.parseObject(jsonstr);
        JSONObject records = firstObj.getJSONObject("records");
        JSONArray list = records.getJSONArray("list");
        WorkRecord wr = new WorkRecord();
        FormItem form = new FormItem();
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                String record_id = list.getJSONObject(i).getString("record_id");
                long create_time = list.getJSONObject(i).getLong("create_time");
                String title = list.getJSONObject(i).getString("title");
                String url = list.getJSONObject(i).getString("url");
                wr.setRecord_id(record_id);
                wr.setCreate_time(create_time);
                wr.setTitle(title);
                wr.setUrl(url);
                JSONArray forms = list.getJSONObject(i).getJSONArray("forms");
                List<FormItem> flist = new ArrayList<>();
                for (int j = 0; j < forms.size(); j++) {
                    String f_title = forms.getJSONObject(j).getString("title");
                    String content = forms.getJSONObject(j).getString("content");
                    form.setTitle(f_title);
                    form.setContent(content);
                    flist.add(form);
                }
                wr.setForm(flist);
                wrlist.add(wr);
            }
        }
        boolean has_more = records.getBoolean("has_more");
        if(has_more){
            getAllWorkRecordByUid(userid,wrlist,offset+limit);
        }
    }
}
