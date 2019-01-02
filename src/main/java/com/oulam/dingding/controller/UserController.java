package com.oulam.dingding.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.Leave_Employer;
import com.oulam.dingding.bean.User;
import com.oulam.dingding.config.Constant;
import com.oulam.dingding.exception.DingTalkEncryptException;
import com.oulam.dingding.exception.DingYunUserException;
import com.oulam.dingding.service.DepartmentService;
import com.oulam.dingding.service.UserService;
import com.oulam.dingding.utils.DingTalkEncryptor;
import com.oulam.dingding.utils.Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *钉钉用户相关(查询用户,删除用户,添加用户,更新用户,获取全部离职人员信息,获取通讯录权限范围)
 */
@Controller
@RequestMapping(value = "/ding/user")
public class UserController {
    protected static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    /**
     * 查询用户
     * @param :钉钉中的用户id
     * @return 用户相关信息
     */
    @ResponseBody
    @RequestMapping(value = "/get", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public User getUser(@RequestParam("userid") String userid) throws DingYunUserException{
        return userService.getDingDingUser(userid);
    }
    /**
     * 删除用户
     * @param :钉钉中的用户id
     * @return 用户相关信息
     */
    @ResponseBody
    @RequestMapping(value = "/del", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public int deleteUser(@RequestParam("userid") String userid) throws DingYunUserException {
        return userService.deleteUser(userid);
    }
    /**
     * 添加用户
     * @param :用户的id,用户的姓名name,钉钉中的部门id,用户的电话mobile,用户的邮件email
     * @return 用户相关信息
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int addUser(@RequestBody Map<String,Object> reqMap) throws DingYunUserException {
        Map<String,Object> map = new HashMap();
        String userid = (String) reqMap.get("userid");
        String name = (String) reqMap.get("name");
        List<Integer> ds =  JSONArray.parseArray(reqMap.get("department").toString(),Integer.class);
        Integer[] departments = ds.toArray(new Integer[ds.size()]);
        int[] depars = ArrayUtils.toPrimitive(departments);
        map.put("department",depars);
        String mobile = (String) reqMap.get("mobile");
        String email = (String) reqMap.get("email");
        if(userid!=null){
            map.put("userid",userid);
        }
        if(name!=null){
            map.put("name",name);
        }
        if(depars.length!=0){
            map.put("department",depars);
        }
        if(mobile!=null){
            map.put("mobile",mobile);
        }
        if(email!=null){
            map.put("email",email);
        }
        return userService.addUser(map);
    }
    /**
     * 更新用户
     * @param :用户的id,用户的姓名name,钉钉中的部门id,用户的电话mobile,用户的邮件email
     * @return 用户相关信息
     */
    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int updateUser(@RequestBody Map<String,Object> reqMap) throws DingYunUserException {
        Map<String,Object> map = new HashMap();
        String userid = (String) reqMap.get("userid");
        String name = (String) reqMap.get("name");
        List<Integer> ds =  JSONArray.parseArray(reqMap.get("department").toString(),Integer.class);
        Integer[] departments = ds.toArray(new Integer[ds.size()]);
        int[] depars = ArrayUtils.toPrimitive(departments);
        map.put("department",depars);
        String mobile = (String) reqMap.get("mobile");
        String email = (String) reqMap.get("email");
        if(userid!=null){
            map.put("userid",userid);
        }
        if(name!=null){
            map.put("name",name);
        }
        if(depars.length!=0){
            map.put("department",depars);
        }
        if(mobile!=null){
            map.put("mobile",mobile);
        }
        if(email!=null){
            map.put("email",email);
        }
        return userService.updateUser(map);
    }
    /**
     * 获取全部离职人员信息
     * @return 离职人员信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/leave", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public List<Leave_Employer> getEmployerLeave(HttpServletRequest request) throws DingYunUserException {
        List<Leave_Employer> les = new ArrayList<>();
        userService.getEmployerLeave(les,0,50);
        return les;
    }
    /**
     * 获取通讯录权限范围
     */
    @ResponseBody
    @RequestMapping(value = "/range", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public String getUserAuth(){
        return userService.getUserAuthor();
    }
    /**
     * 添加企业待入职员工
     * @param :用户的姓名name,用户的电话mobile,预期入职时间pre_entry_time，操作人op_userid,扩展信息extend_info
     * @return 返回是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/wait", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public boolean addEmployerWaite(HttpServletRequest request){
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String date = request.getParameter("pre_entry_time");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date pre_entry_time = null;
        try {
            pre_entry_time = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String op_userid = request.getParameter("op_userid");
        String extend_info = request.getParameter("extend_info");
        Map map = new HashMap();
        map.put("name",name);
        map.put("mobile",mobile);
        map.put("pre_entry_time",pre_entry_time);
        map.put("op_userid",op_userid);
        map.put("extend_info",extend_info);
        return userService.addNewMan(map);
    }
    /**
     * 钉钉业务回调相关 Map<String,Object> 需要添加相关E应用
     */
    @ResponseBody
    @RequestMapping(value = "/callback", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public Map<String, String> getCallBack(@RequestParam(value = "signature", required = false) String signature,
                              @RequestParam(value = "timestamp", required = false) String timestamp,
                              @RequestParam(value = "nonce", required = false) String nonce,
                              @RequestBody(required = false) JSONObject json){//通讯录事件回调
        String params = " signature:"+signature + " timestamp:"+timestamp +" nonce:"+nonce+" json:"+json;
        log.info(params);
        Map<String, String> result = null;
        DingTalkEncryptor dingTalkEncryptor;
        try {
            dingTalkEncryptor = new DingTalkEncryptor(Constant.TOKEN, Constant.ENCODING_AES_KEY, Constant.SUITE_KEY);
            result = dingTalkEncryptor.getEncryptedMap("sucess", System.currentTimeMillis(), Utils.getRandomStr(8));
        } catch (DingTalkEncryptException e) {
            e.printStackTrace();
        }
        return result;
    }
}
