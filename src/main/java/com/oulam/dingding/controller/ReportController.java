package com.oulam.dingding.controller;

import com.oulam.dingding.bean.Report;
import com.oulam.dingding.bean.ReportTemplate;
import com.oulam.dingding.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *日志相关(获取用户日志数据,获取用户可见的日志模板,获取用户日志未读数)
 */
@Controller
@RequestMapping(value = "/ding/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    /**
     * 获取用户日志数据
     * @param :开始时间start_time,结束时间end_time,日志名称template_name
     * @return 返回该用户日志数据列表
     */
    @ResponseBody
    @RequestMapping(value = "/getlist", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<Report> getReportList(HttpServletRequest request){//获取用户日志数据
        long start_time = Long.parseLong(request.getParameter("start_time"));
        long end_time = Long.parseLong(request.getParameter("end_time"));
        String template_name = request.getParameter("template_name");
        String userid = request.getParameter("userid");
        int cursor = Integer.parseInt(request.getParameter("cursor"));
        int size = Integer.parseInt(request.getParameter("size"));
        List<Report> list = new ArrayList<>();
        Map<String,Object> params = new HashMap();
        if(userid!=null){
            params.put("userid",userid);
        }
        params.put("start_time",start_time);
        params.put("end_time",end_time);
        params.put("template_name",template_name);
        params.put("cursor",cursor);
        params.put("size",size);
        reportService.getReportList(list,params);
        return list;
    }
    /**
     * 获取用户可见的日志模板
     * @param :用户userid,偏移量offset,大小size
     * @return 返回该志模板数据列表
     */
    @ResponseBody
    @RequestMapping(value = "/gettemplate", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<ReportTemplate> getReportTemplateoList(HttpServletRequest request){//获取用户可见的日志模板
        List<ReportTemplate> list = new ArrayList<>();
        String userid = request.getParameter("userid");
        int offset = Integer.parseInt(request.getParameter("offset"));
        int size = Integer.parseInt(request.getParameter("size"));
        Map<String,Object> params = new HashMap();
        params.put("userid",userid);
        params.put("offset",offset);
        params.put("size",size);
        reportService.getReportTemplateByUser(list,params);
        return list;
    }
    /**
     * 获取用户日志未读数
     * @param :用户userid
     * @return 返回码
     */
    @ResponseBody
    @RequestMapping(value = "/getnotreadnum", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public int getUserReportNotRead(HttpServletRequest request){//获取用户日志未读数
        String userid = request.getParameter("userid");
        return reportService.getUserReportNotRead(userid);
    }
}
