package com.oulam.dingding.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.Role;
import com.oulam.dingding.bean.RoleGroup;
import com.oulam.dingding.bean.User;
import com.oulam.dingding.dao.RoleGroupMapper;
import com.oulam.dingding.dao.RoleMapper;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色服务相关
 */
@Service
public class RoleService {
    protected static final Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleGroupMapper roleGroupMapper;

    /**
     * 通过角色获取钉钉用户
     * @param role_id
     * @param size
     * @param offset
     * @return
     */
    public List<User> getUsersByRole(String role_id, int size, int offset) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/role/simplelist?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role_id", Long.valueOf(role_id));
        jsonObject.put("size", size);
        jsonObject.put("offset", offset);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        //System.out.println("jsonstr=" + jsonstr);
        JSONObject firstobj = JSONObject.parseObject(jsonstr);
        JSONObject result = firstobj.getJSONObject("result");
        //System.out.println("result=" + result.toJSONString());
        JSONArray list = result.getJSONArray("list");
        //System.out.println("list=" + list.toString());
        List<String> uidlist = new ArrayList();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String userid = list.getJSONObject(i).getString("userid");
                uidlist.add(userid);
            }
            List<User> ulist = userService.getUserListByIds(uidlist);
            return ulist;
        } else {
            return null;
        }
    }
    /**
     * 获取钉钉角色组列表
     * @param size
     * @param offset
     * @return
     */
    public List<RoleGroup> getRoleRoupList(int size, int offset) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/role/list?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("size", size);
        jsonObject.put("offset", offset);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        log.info("钉钉角色组列表===>" + jsonstr);
        JSONObject firstobj = JSONObject.parseObject(jsonstr);
        JSONObject result = firstobj.getJSONObject("result");
        JSONArray rolegroups = result.getJSONArray("list");
        List<RoleGroup> rgs = new ArrayList<>();
        for (int i = 0; i < rolegroups.size(); i++) {
            RoleGroup rg = new RoleGroup();
            JSONObject rolegroup = rolegroups.getJSONObject(i);
            Long groupid = rolegroup.getLong("groupId");
            System.out.println("groupid=" + groupid);
            rg.setGroupid(groupid);
            String groupname = rolegroup.getString("name");
            rg.setName(groupname);
            JSONArray roles = rolegroup.getJSONArray("roles");
            List<Role> roleList = new ArrayList<>();
            for (int j = 0; j < roles.size(); j++) {
                Role role = new Role();
                role.setGroupid(groupid);
                role.setRid(roles.getJSONObject(j).getLong("id"));
                role.setRname(roles.getJSONObject(j).getString("name"));
                roleList.add(role);
            }
            rg.setRoles(roleList);
            rgs.add(rg);
        }
        return rgs;
    }
    /**
     * 创建角色组
     * @param name
     * @return
     */
    public RoleGroup insertDingRoleGroup(String name) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/role/add_role_group?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        JSONObject json = JSONObject.parseObject(jsonstr);
        long groupid = json.getLong("groupId");
        RoleGroup rg = new RoleGroup();
        rg.setGroupid(groupid);
        rg.setName(name);
        roleGroupMapper.insertSelective(rg);
        return rg;
    }
    /**
     * 创建角色
     * @param roleName
     * @param groupId
     * @return
     */
    public Role insertDingRole(String roleName, long groupId) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/role/add_role?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleName", roleName);
        jsonObject.put("groupId", groupId);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        log.info("创建角色结果"+jsonstr);
        JSONObject json = JSONObject.parseObject(jsonstr);
        long rid = json.getLong("roleId");
        Role role = new Role();
        role.setRname(roleName);
        role.setGroupid(groupId);
        role.setRid(rid);
        roleMapper.insertSelective(role);
        return role;
    }
    /**
     * 更新角色
     * @param roleName
     * @param roleId
     * @return
     */
    public Role updateDingRole(String roleName, long roleId) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/role/update_role?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleName", roleName);
        jsonObject.put("roleId", roleId);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        log.info("更新角色结果"+jsonstr);
        JSONObject json = JSONObject.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        Role role = roleMapper.selectByRid(roleId);
        role.setRname(roleName);
        if(errcode == 0){
            roleMapper.updateByPrimaryKey(role);
        }
        return role;
    }
    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public String delDingRole(long roleId) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/role/deleterole?access_token=" + access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleId", roleId);
        String jsonstr = HttpClientUtil.doPost(dingDingurl, jsonObject);
        log.info("删除角色结果"+jsonstr);
        JSONObject json = JSONObject.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        String errmsg = json.getString("errmsg");
        if(errcode == 0){
            roleMapper.deleteByRid(roleId);
            return  errmsg;
        }else{
            return errmsg;
        }
    }
    /**
     * 往云表插入角色组
     * @param list
     * @return
     */
    public int insertRoleGroupBatch(List<RoleGroup> list) {
        return roleGroupMapper.insertBatch(list);
    }
    /**
     * 往云表插入角色
     * @param list
     * @return
     */
    public int insertRoleBatch(List<Role> list) {
        return roleMapper.insertBatch(list);
    }
}