package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.Report;
import com.oulam.dingding.bean.ReportContent;
import com.oulam.dingding.bean.ReportTemplate;
import com.oulam.dingding.bean.User;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.transform.Templates;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 日志相关服务
 */
@Service
public class ReportService {
    protected static final Logger log = LoggerFactory.getLogger(ReportService.class);
    /**
     * 获取用户日志数据
     * @param list
     * @param params
     */
    public void getReportList(List<Report> list, Map<String,Object> params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/report/list?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start_time",params.get("start_time"));
        jsonObject.put("end_time",params.get("end_time"));
        jsonObject.put("template_name",params.get("template_name"));
        jsonObject.put("userid",params.get("userid"));
        jsonObject.put("cursor",params.get("cursor"));
        jsonObject.put("size",params.get("size"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        JSONObject result = json.getJSONObject("result");
        JSONArray data_list = result.getJSONArray("data_list");
        List<Report> reports = new ArrayList<>();
        for(int i = 0;i<data_list.size();i++){
            Report report = new Report();
            JSONObject r = data_list.getJSONObject(i);
            JSONArray cs = r.getJSONArray("contents");
            List<ReportContent> contents = new ArrayList<>();
            for(int j = 0;j<cs.size();j++){
                ReportContent content = new ReportContent();
                JSONObject c = cs.getJSONObject(j);
                content.setSort(c.getString("sort"));
                content.setType(c.getString("type"));
                content.setValue(c.getString("value"));
                content.setKey(c.getString("key"));
                contents.add(content);
            }
            report.setContents(contents);
            report.setRemark(r.getString("remark"));
            report.setTemplate_name(r.getString("template_name"));
            report.setDept_name(r.getString("dept_name"));
            report.setCreator_name(r.getString("creator_name"));
            report.setCreator_id(r.getString("creator_id"));
            report.setCreate_time(r.getLong("create_time"));
            report.setReport_id(r.getString("report_id"));
            reports.add(report);
        }
        boolean has_more = result.getBoolean("has_more");
        if(has_more){
            int next_cursor = result.getInteger("next_cursor");
            params.replace("cursor",next_cursor);
            getReportList(list,params);
        }
    }
    /**
     * 获取用户可见的日志模板
     * @param list
     * @param params
     */
    public void getReportTemplateByUser(List<ReportTemplate> list,Map<String,Object> params){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/report/template/listbyuserid?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",params.get("userid"));
        jsonObject.put("offset",params.get("offset"));
        jsonObject.put("size",params.get("size"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        JSONObject result = json.getJSONObject("result");
        JSONArray templateList = result.getJSONArray("template_list");
        for(int i = 0;i<templateList.size();i++){
            JSONObject temp = templateList.getJSONObject(i);
            ReportTemplate template = new ReportTemplate();
            template.setName(temp.getString("name"));
            template.setIcon_url(temp.getString("icon_url"));
            template.setReport_code(temp.getString("report_code"));
            template.setUrl(temp.getString("url"));
            list.add(template);
        }
        Integer next_cursor = result.getInteger("next_cursor");
        if(next_cursor!=null){
            params.replace("offset",next_cursor);
            getReportTemplateByUser(list,params);
        }
    }
    /**
     * 获取用户日志未读数
     * @param userid
     * @return
     */
    public int getUserReportNotRead(String userid){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/report/getunreadcount?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",userid);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        int count = json.getInteger("count");
        return count;
    }
}
