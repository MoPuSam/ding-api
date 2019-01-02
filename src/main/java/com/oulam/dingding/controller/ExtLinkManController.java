package com.oulam.dingding.controller;

import com.alibaba.fastjson.JSONArray;
import com.oulam.dingding.bean.ExtLinkMan;
import com.oulam.dingding.service.ExtLinkManService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *外部联系人相关(获取外部联系人列表,获取外部联系人详情,添加外部联系人,更新外部联系人,删除外部联系人)
 */
@Controller
@RequestMapping(value = "/ding/extcontact")
public class ExtLinkManController {
    @Autowired
    private ExtLinkManService extLinkManService;
    /**
     *获取外部联系人列表
     * @param :长度size,偏移量offset
     */
    @ResponseBody
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public List<ExtLinkMan> getExtLinkManList(HttpServletRequest request){
        int size = Integer.parseInt(request.getParameter("size"));
        int offset = Integer.parseInt(request.getParameter("offset"));
        return extLinkManService.getExtLinkManList(size,offset);
    }
    /**
     * 获取外部联系人详情
     * @param :钉钉的外部联系人user_id
     */
    @ResponseBody
    @RequestMapping(value = "/get", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public ExtLinkMan getExtLinkManByID(HttpServletRequest request){
        String user_id = request.getParameter("user_id");
        return extLinkManService.getExtLinkManByID(user_id);
    }
    /**
     * 添加外部联系人
     * @param :职位title,标签列表label_ids,共享给的部门share_dept_ids,地址address,备注remark,负责人userId follower_user_id,
     * @param :名称name,手机号国家码state_code,企业名company_name,共享给的员工userId列表share_user_ids,手机号mobile
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String addExtLinkMan(@RequestBody Map<String,Object> reqMap){
        Map<String,Object> params = new HashMap<>();
        String title = reqMap.get("title").toString();
        params.put("title",title);
        List<Integer> lids =  JSONArray.parseArray(reqMap.get("label_ids").toString(),Integer.class);
        Integer[] labelids = lids.toArray(new Integer[lids.size()]);
        int[] label_ids = ArrayUtils.toPrimitive(labelids);
        params.put("label_ids",label_ids);
        List<Integer> sdids =  JSONArray.parseArray(reqMap.get("share_dept_ids").toString(),Integer.class);
        Integer[] sharedeptids = sdids.toArray(new Integer[sdids.size()]);
        int[] share_dept_ids = ArrayUtils.toPrimitive(sharedeptids);
        params.put("share_dept_ids",share_dept_ids);
        String address = reqMap.get("address").toString();
        params.put("address",address);
        String remark = reqMap.get("remark").toString();
        params.put("remark",remark);
        String follower_user_id = reqMap.get("follower_user_id").toString();
        params.put("follower_user_id",follower_user_id);
        String name = reqMap.get("name").toString();
        params.put("name",name);
        String state_code = reqMap.get("state_code").toString();
        params.put("state_code",state_code);
        String company_name = reqMap.get("company_name").toString();
        params.put("company_name",company_name);
        List<String> shareuserids = JSONArray.parseArray(reqMap.get("share_user_ids").toString(),String.class);
        String[] share_user_ids = shareuserids.toArray(new String[shareuserids.size()]);
        params.put("share_user_ids",share_user_ids);
        String mobile = reqMap.get("mobile").toString();
        params.put("mobile",mobile);
        return extLinkManService.addExtLinkMan(params);
    }
    /**
     * 更新外部联系人
     * @param :职位title,标签列表label_ids,共享给的部门share_dept_ids,地址address,备注remark,负责人userId follower_user_id,
     * @param :名称name,手机号国家码state_code,企业名company_name,共享给的员工userId列表share_user_ids,手机号mobile
     */
    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int updateExtLinkMan(@RequestBody Map<String,Object> reqMap){
        Map<String,Object> params = new HashMap<>();
        String user_id = reqMap.get("user_id").toString();
        params.put("user_id",user_id);
        String title = reqMap.get("title").toString();
        params.put("title",title);
        List<Integer> lids =  JSONArray.parseArray(reqMap.get("label_ids").toString(),Integer.class);
        Integer[] labelids = lids.toArray(new Integer[lids.size()]);
        int[] label_ids = ArrayUtils.toPrimitive(labelids);
        params.put("label_ids",label_ids);
        List<Integer> sdids =  JSONArray.parseArray(reqMap.get("share_dept_ids").toString(),Integer.class);
        Integer[] sharedeptids = sdids.toArray(new Integer[sdids.size()]);
        int[] share_dept_ids = ArrayUtils.toPrimitive(sharedeptids);
        params.put("share_dept_ids",share_dept_ids);
        String address = reqMap.get("address").toString();
        params.put("address",address);
        String remark = reqMap.get("remark").toString();
        params.put("remark",remark);
        String follower_user_id = reqMap.get("follower_user_id").toString();
        params.put("follower_user_id",follower_user_id);
        String name = reqMap.get("name").toString();
        params.put("name",name);
        String state_code = reqMap.get("state_code").toString();
        params.put("state_code",state_code);
        String company_name = reqMap.get("company_name").toString();
        params.put("company_name",company_name);
        List<String> shareuserids = JSONArray.parseArray(reqMap.get("share_user_ids").toString(),String.class);
        String[] share_user_ids = shareuserids.toArray(new String[shareuserids.size()]);
        params.put("share_user_ids",share_user_ids);
        String mobile = reqMap.get("mobile").toString();
        params.put("mobile",mobile);
        return extLinkManService.updateExtLinkMan(params);
    }
    /**
     * 删除外部联系人
     * @param :钉钉的外部联系人user_id
     */
    @ResponseBody
    @RequestMapping(value = "/del", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int delExtLinkMan(HttpServletRequest request){
        String user_id = request.getParameter("user_id");
        return extLinkManService.delExtLinkManByID(user_id);
    }
}
