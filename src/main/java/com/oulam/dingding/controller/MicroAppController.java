package com.oulam.dingding.controller;

import com.oulam.dingding.bean.MicroApp;
import com.oulam.dingding.exception.DingYunMicroAppException;
import com.oulam.dingding.service.MicroAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 *微应用相关(获取应用列表,新增微应用,修改微应用,删除微应用)
 */
@Controller
@RequestMapping(value = "/ding/microapp")
public class MicroAppController {
    @Autowired
    private MicroAppService microAppService;
    /**
     * 获取应用列表
     */
    @ResponseBody
    @RequestMapping(value = "/getAll", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<MicroApp> getAllMicroApp(HttpServletRequest request){
        return microAppService.getMicroApps();
    }
    /**
     * 新增微应用
     * @param :微应用图标appIcon,微应用名称appName,微应用描述appDesc,应用移动端主页地址homepageUrl
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int addMicroApp(HttpServletRequest request) throws DingYunMicroAppException {
        String appIcon = request.getParameter("appIcon");
        String appName = request.getParameter("appName");
        String appDesc = request.getParameter("appDesc");
        //应用的移动端主页
        String homepageUrl = request.getParameter("homepageUrl");
        HashMap map = new HashMap();
        map.put("appIcon",appIcon);
        map.put("appName",appName);
        map.put("appDesc",appDesc);
        map.put("homepageUrl",homepageUrl);
        return microAppService.addMicroApp(map);
    }
    /**
     * 修改微应用
     * @param :微应用图标appIcon,微应用名称appName,微应用描述appDesc,应用移动端主页地址homepageUrl,PC端主业地址pcHomepageUrl
     * @param :应用的OA后台管理端主页ompLink,微应用agentId
     */
    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int updateMicroApp(HttpServletRequest request) throws DingYunMicroAppException {
        String appIcon = request.getParameter("appIcon");
        String appName = request.getParameter("appName");
        String appDesc = request.getParameter("appDesc");
        //应用的移动端主页
        String homepageUrl = request.getParameter("homepageUrl");
        //应用的PC端主页
        String pcHomepageUrl = request.getParameter("pcHomepageUrl");
        //应用的OA后台管理端主页
        String ompLink = request.getParameter("ompLink");
        int agentId = Integer.parseInt(request.getParameter("agentId"));
        HashMap map = new HashMap();
        if(appIcon!=null) {
            map.put("appIcon", appIcon);
        }
        if(appName!=null) {
            map.put("appName", appName);
        }
        if(appDesc!=null) {
            map.put("appDesc", appDesc);
        }
        if(homepageUrl!=null) {
            map.put("homepageUrl", homepageUrl);
        }
        if(pcHomepageUrl!=null) {
            map.put("pcHomepageUrl", pcHomepageUrl);
        }
        if(ompLink!=null) {
            map.put("ompLink", ompLink);
        }
        if(agentId!=0) {
            map.put("agentId", agentId);
        }
        return microAppService.addMicroApp(map);
    }
    /**
     * 删除微应用
     * @param :微应用agentId
     */
    @ResponseBody
    @RequestMapping(value = "/del", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int delMicroApp(HttpServletRequest request) throws DingYunMicroAppException {
        long agentId = Long.parseLong(request.getParameter("agentId"));
        return microAppService.delMicroApp(agentId);
    }
}
