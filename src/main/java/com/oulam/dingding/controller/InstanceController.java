package com.oulam.dingding.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.*;
import com.oulam.dingding.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审批相关控制器
 */
@Controller
@RequestMapping(value = "/ding/instance")
public class InstanceController {
    @Autowired
    private InstanceService instanceService;
    /**
     *获取审批实例
     */
    @ResponseBody
    @RequestMapping(value = "/get", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public String getInstance(HttpServletRequest request){
        String id = request.getParameter("id");
        return instanceService.getInstance(id);
    }
    /**
     *批量获取时间段内的审批实例
     */
    @ResponseBody
    @RequestMapping(value = "/getbyday", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<Instance> getInstanceByDay(HttpServletRequest request){
        long startTime = Long.parseLong(request.getParameter("startTime"));
        long endTime = Long.parseLong(request.getParameter("endTime"));
        String process_code = request.getParameter("process_code");
        return instanceService.getInstancesObjs(process_code,startTime,endTime);
    }
    //获取用户可见的审批模板
    @ResponseBody
    @RequestMapping(value = "/getbyuser", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<ProcessMode> getInstanceByUser(HttpServletRequest request){
        String userid = request.getParameter("userid");
        int offset = Integer.parseInt(request.getParameter("offset"));
        int size = Integer.parseInt(request.getParameter("size"));
        List<ProcessMode> list = new ArrayList<>();
        instanceService.getInstancesObjsByUser(list,userid,offset,size);
        return list;
    }
    //发送审批流
    @ResponseBody
    @RequestMapping(value = "/create", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String createInstance(@RequestBody Map<String,Object> reqmap){
        String agent_id = reqmap.get("agent_id").toString();
        String process_code = reqmap.get("process_code").toString();
        String originator_user_id = reqmap.get("originator_user_id").toString();
        String dept_id = reqmap.get("dept_id").toString();
        String approvers = reqmap.get("approvers").toString();
        String cc_list = reqmap.get("cc_list").toString();
        String cc_position = reqmap.get("cc_position").toString();
        String formcomponentvalues = reqmap.get("form_component_values").toString();
        JSONArray fcvalues = JSON.parseArray(formcomponentvalues);
        List<FormComponent> form_component_values = new ArrayList<>();
        for(int i = 0;i<fcvalues.size();i++){
            FormComponent form_component_value = new FormComponent();
            JSONObject fcv = fcvalues.getJSONObject(i);
            form_component_value.setName(fcv.getString("name"));
            form_component_value.setValue(fcv.getString("value"));
            form_component_value.setExt_value(fcv.getString("ext_value"));
            form_component_values.add(form_component_value);
        }
        Map<String,Object> params = new HashMap<>();
        if(agent_id!=null) {
            params.put("agent_id", agent_id);
        }
        params.put("process_code",process_code);
        params.put("originator_user_id",originator_user_id);
        params.put("dept_id",dept_id);
        params.put("approvers",approvers);
        if(cc_list!=null) {
            params.put("cc_list", cc_list);
        }
        if(cc_position!=null) {
            params.put("cc_position", cc_position);
        }
        params.put("form_component_values",form_component_values);
        return instanceService.createInstance(params);
    }
    //复制审批流
    @ResponseBody
    @RequestMapping(value = "/copy", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public InstanceProcess copyInstance(@RequestBody Map<String,Object> reqmap){
        int agent_id = Integer.parseInt(reqmap.get("agent_id").toString());
        String process_code = reqmap.get("process_code").toString();
        String biz_category_id = reqmap.get("biz_category_id").toString();
        String process_name = reqmap.get("process_name").toString();
        String description = reqmap.get("description").toString();
        Map<String,Object> params = new HashMap();
        params.put("agent_id",agent_id);
        params.put("process_code",process_code);
        if(biz_category_id!=null) {
            params.put("biz_category_id", biz_category_id);
        }
        if(process_name!=null) {
            params.put("process_name", process_name);
        }
        if(description!=null) {
            params.put("description", description);
        }
        return instanceService.copyInstance(params);
    }

}
