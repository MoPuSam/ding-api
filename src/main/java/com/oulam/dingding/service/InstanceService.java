package com.oulam.dingding.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.*;
import com.oulam.dingding.dao.UserMapper;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取审批实例服务
 */
@Service
public class InstanceService {

    protected static final Logger log = LoggerFactory.getLogger(InstanceService.class);
    @Autowired
    private UserMapper mapper;
    /**
     * 获取审批类型
     */
    public String getInstance(String id){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/processinstance/get?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("process_instance_id",id);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        String process_instance = firstJson.getString("process_instance");
        JSONObject secJson = JSONObject.parseObject(process_instance);
        JSONArray dlist = secJson.getJSONArray("form_component_values");
        String tValue = dlist.get(2).toString();
        JSONObject thrJson = JSONObject.parseObject(tValue);
        String value = thrJson.getString("value");
        return value;
    }
    public List<String> getInstancesIdsByCusor(List<String> firList, String process_code, long startTime, long endTime, int cursor) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/processinstance/listids?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("process_code",process_code);
        jsonObject.put("start_time",startTime);
        jsonObject.put("end_time",endTime);
        if(cursor!=0) {
            jsonObject.put("cursor", cursor);
        }
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        String result = firstJson.getString("result");
        JSONObject secJson = JSONObject.parseObject(result);
        JSONArray list = secJson.getJSONArray("list");
        Integer next_cursor = secJson.getInteger("next_cursor");
        for(int i = 0;i < list.size();i++){
            firList.add(list.get(i).toString());
        }
        if(next_cursor!=null){
            getInstancesIdsByCusor(firList,process_code,startTime,endTime,next_cursor);
        }
        return firList;
    }

    /**
     * 获取审批实例
     */
    public Instance getDingInstance(String id){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/processinstance/get?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("process_instance_id",id);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        Instance instance = new Instance();
        instance.setId(id);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        String process_instance = firstJson.getString("process_instance");
        JSONObject secJson = JSONObject.parseObject(process_instance);
        instance.setTitle(secJson.getString("title"));
        instance.setCreate_time(secJson.getString("create_time"));
        instance.setFinish_time(secJson.getString("finish_time"));
        instance.setOriginator_userid(secJson.getString("originator_userid"));
        instance.setOriginator_dept_id(secJson.getString("originator_dept_id"));
        instance.setOriginator_dept_name(secJson.getString("originator_dept_name"));

        JSONArray fcv = secJson.getJSONArray("form_component_values");
        String[] fcvs =new String[fcv.size()];
        for(int i = 0;i<fcv.size();i++){
            fcvs[i] = fcv.get(i).toString();
        }
        instance.setForm_component_values(fcvs);

        JSONArray or = secJson.getJSONArray("operation_records");
        String[] ors =new String[or.size()];
        for(int i = 0;i<or.size();i++){
            ors[i] = or.get(i).toString();
        }
        instance.setOperation_records(ors);
        JSONArray task = secJson.getJSONArray("tasks");
        List<Task> taskList = JSONObject.parseArray(task.toJSONString(),Task.class);
        instance.setTasks(taskList);
        instance.setResult(secJson.getString("result"));
        instance.setStatus(secJson.getString("status"));
        return instance;
    }
    /**
     * 批量获取时间段内的审批实例
     * @param process_code
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Instance> getInstancesObjs(String process_code,long startTime, long endTime) {
        List<String> firList = new ArrayList<>();
        getInstancesIdsByCusor(firList ,process_code,startTime,endTime,0);
        List<Instance> instances = new ArrayList<>();
        for(String id:firList){
            instances.add(getDingInstance(id));
        }
        return instances;
    }
    /**
     * 获取用户可见的审批模板
     * @param list
     * @param userid
     * @param offset
     * @param size
     */
    public void getInstancesObjsByUser(List<ProcessMode> list, String userid, int offset, int size) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/process/listbyuserid?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",userid);
        jsonObject.put("offset",offset);
        jsonObject.put("size",size);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        JSONObject result = json.getJSONObject("result");
        JSONArray process_list = result.getJSONArray("process_list");
        for(int i = 0;i < process_list.size();i++){
            ProcessMode p = new ProcessMode();
            JSONObject pm = process_list.getJSONObject(i);
            p.setName(pm.getString("name"));
            p.setIcon_url(pm.getString("icon_url"));
            p.setProcess_code(pm.getString("process_code"));
            p.setUrl(pm.getString("url"));
            list.add(p);
        }
        Integer next_cursor = result.getInteger("next_cursor");
        if(next_cursor!=null){
            getInstancesObjsByUser(list,userid,next_cursor,size);
        }
    }
    /**
     * 发起审批实例
     * @param params
     * @return
     */
    public String createInstance(Map params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/processinstance/create?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",params.get("agent_id"));
        jsonObject.put("process_code",params.get("process_code"));
        String userid = (String) params.get("originator_user_id");
        User user = mapper.selectById(userid);
        jsonObject.put("originator_user_id",userid);
        jsonObject.put("dept_id",user.getDepartment());
        jsonObject.put("approvers",params.get("approvers"));
        jsonObject.put("cc_list",params.get("cc_list"));
        jsonObject.put("cc_position",params.get("cc_position"));
        jsonObject.put("form_component_values",params.get("form_component_values"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        String process_instance_id = json.getString("process_instance_id");
        return process_instance_id;
    }
    /**
     * 更新审批流
     * @param params
     * @return
     */
    public int syncInstance(Map params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/process/sync?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",params.get("agent_id"));
        jsonObject.put("src_process_code",params.get("src_process_code"));
        jsonObject.put("target_process_code",params.get("target_process_code"));
        jsonObject.put("biz_category_id",params.get("biz_category_id"));
        jsonObject.put("process_name",params.get("process_name"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        return errcode;
    }
    /**
     * 复制审批流
     * @param params
     * @return
     */
    public InstanceProcess copyInstance(Map params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/process/copy?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",params.get("agent_id"));
        jsonObject.put("process_code",params.get("process_code"));
        jsonObject.put("biz_category_id",params.get("biz_category_id"));
        jsonObject.put("process_name",params.get("process_name"));
        jsonObject.put("description",params.get("description"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("复制审批流返回结果======================>"+jsonstr);
        JSONObject json = JSON.parseObject(jsonstr);
        JSONObject result = json.getJSONObject("result");
        InstanceProcess ip = new InstanceProcess();
        ip.setProcess_code(result.getString("process_code"));
        ip.setDescription(result.getString("description"));
        ip.setName(result.getString("name"));
        ip.setBiz_category_id(result.getString("biz_category_id"));
        ip.setForm_content(result.getString("form_content"));
        return ip;
    }
}
