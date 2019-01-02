package com.oulam.dingding.controller;

import com.oulam.dingding.bean.FormItem;
import com.oulam.dingding.bean.WorkRecord;
import com.oulam.dingding.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *待办事项相关(发起待办信息,更新待办,获取对应用户下的待办)
 */
@Controller
@RequestMapping(value = "/ding/workrecord")
public class WorkRecordController {
    @Autowired
    private WorkRecordService workRecordService;
    /**
     * 发起待办信息
     * @param :钉钉用户userid,创建时间create_time,标题title
     * @return 返回信息
     */
    @ResponseBody
    @RequestMapping(value = "/workrecord/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String addWorkRecord(HttpServletRequest request){
        String userid = request.getParameter("userid");
        long create_time = Long.parseLong(request.getParameter("create_time"));
        String title = request.getParameter("title");
        Map map = new HashMap();
        map.put("userid",userid);
        map.put("create_time",create_time);
        map.put("title",title);
        List<FormItem> list = new ArrayList<>(5);
        String f_title = request.getParameter("f_title");
        String content = request.getParameter("content");
        FormItem formItem = new FormItem();
        formItem.setTitle(f_title);
        formItem.setContent(content);
        list.add(formItem);
        return workRecordService.addWorkRecord(map,list);
    }
    /**
     * 更新待办
     * @param :钉钉用户userid,待办record_id
     * @return 返回信息
     */
    @ResponseBody
    @RequestMapping(value = "/workrecord/update", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String updateWorkRecord(HttpServletRequest request){
        String userid = request.getParameter("userid");
        String record_id = request.getParameter("record_id");
        return workRecordService.updateWorkRecord(userid,record_id);
    }
    /**
     * 获取对应用户下的待办
     * @param :钉钉用户userid
     * @return 待办信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/workrecord/getbyuid", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public List<WorkRecord> getWorkRecordByUid(HttpServletRequest request){
        String userid = request.getParameter("userid");
        List<WorkRecord> wrlist = new ArrayList<>();
        workRecordService.getAllWorkRecordByUid(userid,wrlist,0);
        return wrlist;
    }
}
