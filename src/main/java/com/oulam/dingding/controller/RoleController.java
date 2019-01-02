package com.oulam.dingding.controller;

import com.oulam.dingding.bean.Role;
import com.oulam.dingding.bean.RoleGroup;
import com.oulam.dingding.bean.User;
import com.oulam.dingding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色相关控制器
 */
@Controller
@RequestMapping(value = "/ding/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 通过角色获取用户列表
     * @param :角色role_id,大小size,偏移量offset
     * @return 用户数据列表
     */
    @ResponseBody
    @RequestMapping(value = "/getulist", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<User> getRoleList(HttpServletRequest request){
        String role_id = request.getParameter("role_id");
        int size = Integer.valueOf(request.getParameter("size"));
        int offset = Integer.valueOf(request.getParameter("offset"));
        List<User> userList = roleService.getUsersByRole(role_id, size, offset);
        if(userList!=null){
            return userList;
        }else{
            return null;
        }
    }
    /**
     * 往云表与钉钉中添加角色组
     * @param :角色组名称
     * @return 添加的角色组信息
     */
    @ResponseBody
    @RequestMapping(value = "/rolegroup/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public RoleGroup addRoleGroup(HttpServletRequest request){
        String name = request.getParameter("name");
        return roleService.insertDingRoleGroup(name);

    }
    /**
     * 往云表与钉钉中添加角色
     * @param :角色名称roleName,角色组groupId
     * @return 添加的角色组信息
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public Role addRole(HttpServletRequest request){
        String roleName = request.getParameter("roleName");
        long groupId = Long.valueOf(request.getParameter("groupId"));
        return roleService.insertDingRole(roleName,groupId);

    }
    /**
     * 更改云表与钉钉中的角色
     * @param :角色名称roleName,角色组groupId
     * @return 添加的角色信息
     */
    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public Role updateRole(HttpServletRequest request){
        String roleName = request.getParameter("roleName");
        long roleId = Long.valueOf(request.getParameter("roleId"));
        return roleService.updateDingRole(roleName,roleId);
    }
    /**
     * 删除云表与钉钉中的角色
     * @param :角色roleId
     * @return 返回信息
     */
    @ResponseBody
    @RequestMapping(value = "/del", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String delRole(HttpServletRequest request){
        long roleId = Long.valueOf(request.getParameter("roleId"));
        return roleService.delDingRole(roleId);
    }
    /**
     * 获取角色组列表以及角色列表并插入云表
     * @param :大小size,偏移量offset
     * @return 角色组信息列表数据
     */
    @ResponseBody
    @RequestMapping(value = "/rolegroup/getlist", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    @Transactional
    public List<RoleGroup> getRoleGroupList(HttpServletRequest request){
        int size = Integer.valueOf(request.getParameter("size"));
        int offset = Integer.valueOf(request.getParameter("offset"));
        List<RoleGroup> list = roleService.getRoleRoupList(size,offset);
        roleService.insertRoleGroupBatch(list);
        for(RoleGroup group : list){
            roleService.insertRoleBatch(group.getRoles());
        }
        return list;
    }
}
