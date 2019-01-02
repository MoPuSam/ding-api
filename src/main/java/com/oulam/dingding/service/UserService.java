package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.oulam.dingding.bean.*;
import com.oulam.dingding.dao.UserMapper;
import com.oulam.dingding.exception.DingYunUserException;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户相关服务
 */
@Service("userService")
public class UserService {
    protected static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    DepartmentService departmentService;
    @Autowired
    private UserMapper mapper;

    public void insert(List<User> list){
        mapper.batchInsert(list);
    }

    /**
     *获取钉钉用户数据
     */
    public User getDingDingUser(String userid) throws DingYunUserException {
        User user = new User();
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/user/get?access_token="+access_Token+"&userid="+userid;
        String jsonstr = HttpClientUtil.doGet(dingDingurl,null);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode==0) {
            user.setUserid(firstJson.getString("userid"));
            user.setEmail(firstJson.getString("email"));
            user.setJobnumber(firstJson.getString("jobnumber"));
            user.setMobile(firstJson.getString("mobile"));
            user.setName(firstJson.getString("name"));
            String department = firstJson.getString("department");
            String d = department.substring(1, department.length() - 1);
            user.setDepartment(Long.valueOf(d));
            return user;
        }else{
            throw new DingYunUserException(Integer.valueOf(errcode));
        }
    }
    /**
     *获取所有员工的id集合
     */
    public List getIdList(String access_Token) {
        ArrayList<String> userIds = new ArrayList();
        int offset = 0;
        //判断是否分页结束
        String next_cursor = "";
        Boolean hasMore = true;
        while(hasMore){
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("status_list", "2,3");
            mapParam.put("offset", offset);
            mapParam.put("size", 50);
            String userStr = getUserIds(mapParam, access_Token);
            JSONObject firstJson = JSONObject.parseObject(userStr);

            String result = firstJson.getString("result");
            log.info("----------------------------------------result="+result);
            JSONObject secJson = JSONObject.parseObject(result);

            JSONArray ids = secJson.getJSONArray("data_list");
            log.info("----------------------------------------ids="+ids);
            for(int i = 0;i<ids.size();i++){
                userIds.add(ids.get(i).toString());
            }
            next_cursor = secJson.getString("next_cursor");
            log.info("----------------------------------------next_cursor="+next_cursor);

            if(next_cursor!=null){
                offset = Integer.parseInt(next_cursor);
                log.info("----------------------------------------offset="+offset);
            }else{
                hasMore = false;
            }
        }
        return userIds;
    }
    public String getUserIds(Map<String, Object> map ,String access_token_str){
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob?access_token="+access_token_str;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_list",map.get("status_list"));
        jsonObject.put("offset",map.get("offset"));
        jsonObject.put("size",map.get("size"));
        return HttpClientUtil.doPost(dingDingAttendance,jsonObject);//获取所有员工ID
    }
    /**
     * 创建钉钉用户
     */
    public int addUser(Map map) throws DingYunUserException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/user/create?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",map.get("userid"));
        jsonObject.put("name",map.get("name"));
        jsonObject.put("department",map.get("department"));
        jsonObject.put("mobile",map.get("mobile"));
        jsonObject.put("email",map.get("email"));
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        log.info("创建用户返回结果======================>>>>>"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunUserException(Integer.valueOf(errcode));
        }
        return errcode;
    }
    /**
     * 更新钉钉用户
     */
    public int updateUser(Map map) throws DingYunUserException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/user/update?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        if(map.get("userid")!=null) {
            jsonObject.put("userid", map.get("userid"));
        }
        if(map.get("name")!=null) {
        jsonObject.put("name",map.get("name"));
        }
        if(map.get("department")!=null) {
            jsonObject.put("department", map.get("department"));
        }
        if(map.get("mobile")!=null) {
            jsonObject.put("mobile", map.get("mobile"));
        }
        if(map.get("email")!=null) {
            jsonObject.put("email", map.get("email"));
        }
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        log.info("===========================================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunUserException(Integer.valueOf(errcode));
        }
        return errcode;
    }
    /**
     * 删除钉钉用户
     */
    public int deleteUser(String id) throws DingYunUserException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/user/delete?access_token="+access_Token+"&userid="+id;
        String jsonstr = HttpClientUtil.doGet(dingDingurl,null);
        log.info("===========================================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunUserException(Integer.valueOf(errcode));
        }
        return errcode;
    }
    /**
     * 批量删除钉钉用户
     */
    public int batchDeleteUser(List useridlist){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/user/batchdelete?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("useridlist",useridlist);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("===========================================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        return errcode;
    }
    /**
     * 查询企业离职员工列表
     */
    public void getEmployerLeave(List<Leave_Employer> list,int offset,int size) throws DingYunUserException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/querydimission?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("offset",offset);
        jsonObject.put("size",size);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        JSONObject result  = json.getJSONObject("result");
        JSONArray data_list = result.getJSONArray("data_list");
        List<String> ids = new ArrayList<>();
        for(int i = 0; i<data_list.size();i++){
            ids.add(data_list.getString(i));
        }
        List<Leave_Employer> elist = getEmployerInfoLeave(ids);
        list.addAll(elist);
        Integer next_cursor = result.getInteger("next_cursor");
        if(next_cursor!=null){
            getEmployerLeave(list,next_cursor,size);
        }
    }
    /**
     * 获取离职员工离职信息
     */
    public List<Leave_Employer> getEmployerInfoLeave(List<String> ids) throws DingYunUserException {
        String userid_list = "";
        for(String id :ids){
            userid_list=id + ",";
        }
        userid_list = userid_list.substring(0,userid_list.length()-1);
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/listdimission?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid_list",userid_list);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("离职人员id==========>"+jsonstr);
        JSONObject json = JSON.parseObject(jsonstr);
        Leave_Employer le = new Leave_Employer();
        List<Leave_Employer> les = new ArrayList<>();
        int errcode = json.getInteger("errcode");
        if(errcode == 0){
            JSONArray result = json.getJSONArray("result");
            for(int i = 0;i<result.size();i++){
                JSONObject user = result.getJSONObject(i);
                String userid = user.getString("userid");
                le.setUserid(userid);
                long last_work_day = user.getLong("last_work_day");
                Date lwd = new Date(last_work_day);
                le.setLast_work_day(lwd);
                JSONArray dept_list = user.getJSONArray("dept_list");
                List<Department> dlist = new ArrayList<>();
                for(int j = 0;j<dept_list.size();j++){
                    String dept_id = dept_list.getJSONObject(j).getString("dept_id");
                    Department department = departmentService.getDepartById(dept_id);
                    dlist.add(department);
                }
                le.setDlist(dlist);
                String reason_memo = user.getString("reason_memo");
                le.setReason_memo(reason_memo);
                int reason_type = user.getInteger("reason_type");
                le.setReason_type(reason_type);
                int pre_status = user.getInteger("pre_status");
                le.setPre_status(pre_status);
                String handover_userid = user.getString("handover_userid");
                le.setHandover_userid(handover_userid);
                int status = user.getInteger("status");
                le.setStatus(status);
                String main_dept_name = user.getString("main_dept_name");
                le.setMain_dept_name(main_dept_name);
                int main_dept_id = user.getInteger("main_dept_id");
                le.setMain_dept_id(main_dept_id);
                les.add(le);
            }
        }else{
            throw new DingYunUserException(Integer.valueOf(errcode));
        }
        return les;
    }
    /**
     * 添加企业待入职员工
     */
    public boolean addNewMan(Map param){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/addpreentry?access_token="+access_Token;
        PreEntryEmployeeAddParam pr = new PreEntryEmployeeAddParam();
        JSONObject jsonObject = new JSONObject();
        pr.setName((String) param.get("name"));
        pr.setMobile((String) param.get("mobile"));
        pr.setPre_entry_time((Date) param.get("pre_entry_time"));
        pr.setOp_userid((String) param.get("op_userid"));
        pr.setExtend_info((String) param.get("extend_info"));
        jsonObject.put("param",pr);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        boolean success = json.getBoolean("success");
        return success;
    }

    //获取通讯录权限范围
    public String getUserAuthor(){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/auth/scopes?access_token="+access_Token;
        String jsonstr = HttpClientUtil.doGet(dingDingurl,null);
        return jsonstr;
    }

    public User getUserById(String id) {
        return mapper.selectById(id);
    }

    public List<User> getUserListByIds(List<String> uidlist) {
        return mapper.getUserListByIds(uidlist);
    }
}
