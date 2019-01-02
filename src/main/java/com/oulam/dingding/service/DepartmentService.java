package com.oulam.dingding.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.Department;
import com.oulam.dingding.dao.DepartmentMapper;
import com.oulam.dingding.exception.DingYunDepartException;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 钉钉相关服务
 */
@Service
public class DepartmentService {
    protected static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    @Autowired
    private DepartmentMapper mapper;
    public int insert(List<Department> list){
        return mapper.batchInsert(list);
    }
    /**
     * 根据Id获取云表中部门
     */
    public Department getDepartById(String id){
        return mapper.selectById(Long.parseLong(id));
    }
    /**
     * 获取全部部门信息
     */
    public List<Department> getDeparts(){
        List<Department> list = new ArrayList<>();
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/department/list?access_token="+access_Token+"&fetch_child="+true+"&id="+1;
        String jsonstr = HttpClientUtil.doGet(dingDingurl,null);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        JSONArray depars = firstJson.getJSONArray("department");
        for(int i = 0;i<depars.size();i++){
            Department d = new Department();
            String department = depars.get(i).toString();
            JSONObject secJson = JSONObject.parseObject(department);
            d.setdId(secJson.getLong("id"));
            d.setParentid(secJson.getLong("parentid"));
            d.setName(secJson.getString("name"));
            list.add(d);
        }
        log.info("===========================================>>>>>返回结果"+depars);
        return list;
    }
    /**
     * 新增部门信息至钉钉
     */
    public int addDepart(Map map) throws DingYunDepartException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/department/create?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",map.get("name"));
        jsonObject.put("parentid",map.get("parentid"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("===========================================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunDepartException(errcode);
        }
        return errcode;
    }

    /**
     * 修改部门信息至钉钉
     */
    public int updateDepart(Map map) throws DingYunDepartException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/department/update?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",map.get("id"));
        jsonObject.put("name",map.get("name"));
        jsonObject.put("parentid",map.get("parentid"));
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("===========================================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunDepartException(errcode);
        }
        return errcode;
    }
    /**
     * 删除钉钉部门信息(不能删除根部门；不能删除含有子部门、成员的部门)
     */
    public int deleteDepart(String did) throws DingYunDepartException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/department/delete?access_token="+access_Token+"&id="+did;
        String jsonstr = HttpClientUtil.doGet(dingDingurl,null);
        log.info("===========================================>>>>>返回结果"+jsonstr);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunDepartException(errcode);
        }
        return errcode;
    }
    /**
     * 删除钉钉部门及以下子部门信息（不能删除根部门）
     */
    public int deleteDepartRecur(String did) throws DingYunDepartException {
        //获取子部门列表
        String access_Token = HttpClientUtil.getAccessToken();
        int errcode = 0;
        String dingDingurl1 = "https://oapi.dingtalk.com/department/list_ids?access_token="+access_Token+"&id="+did;
        String jsonstr = HttpClientUtil.doGet(dingDingurl1,null);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        JSONArray dlist = firstJson.getJSONArray("sub_dept_id_list");
        if(dlist.size()!=0) {
            for (int i = 0; i < dlist.size(); i++) {
                String ddid = dlist.get(i).toString();
                String url = "https://oapi.dingtalk.com/department/list_ids?access_token="+access_Token+"&id="+ddid;
                String jsonstr0 = HttpClientUtil.doGet(url,null);
                JSONObject firstJson0 = JSONObject.parseObject(jsonstr0);
                JSONArray dlist0 = firstJson0.getJSONArray("sub_dept_id_list");
                if(dlist0.size()>0) {
                    errcode = deleteDepartRecur(ddid);
                    break;
                }
            }
            errcode = deleteDepart(did);
        }else{
            errcode = deleteDepart(did);
        }
        return errcode;
    }
    /**
     * 获取云表内全部部门信息
     */
    public List<Department> getDepartments(){
        return mapper.selectAll();
    }
}
