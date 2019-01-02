package com.oulam.dingding.controller;

import com.oulam.dingding.bean.Department;
import com.oulam.dingding.exception.DingYunDepartException;
import com.oulam.dingding.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钉钉部门相关(添加部门,获取部门列表,修改部门信息,删除部门)
 */
@Controller
@RequestMapping(value = "/ding/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    /**
     * 添加部门
     * @param :部门名称name，父部门parentid
     * @return 钉钉请求的返回码
     */
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int addDepartment(HttpServletRequest request) throws DingYunDepartException {
        Map<String,Object> map = new HashMap();
        String name = request.getParameter("name");
        String parentid = request.getParameter("parentid");
        if(name!=null){
            map.put("name",name);
        }
        if(parentid!=null){
            map.put("parentid",parentid);
        }
        return departmentService.addDepart(map);
    }
    /**
     * 获取部门列表
     * @return 钉钉的部门列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/getall", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public List<Department> getDepartment(){
        return departmentService.getDeparts();
    }
    /**
     * 修改部门信息
     * @param :钉钉部门id，修改的名称，钉钉父部门id
     * @return 钉钉请求的返回码
     */
    @ResponseBody
    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int updateDepartment(HttpServletRequest request) throws DingYunDepartException {
        Map<String,Object> map = new HashMap();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String parentid = request.getParameter("parentid");
        if(id!=null){
            map.put("id",id);
        }
        if(name!=null){
            map.put("name",name);
        }
        if(parentid!=null){
            map.put("parentid",parentid);
        }
        return departmentService.updateDepart(map);
    }
    /**
     * 删除部门
     * @param :传入钉钉部门id
     * @return 钉钉请求的返回码
     */
    @ResponseBody
    @RequestMapping(value = "/del", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public int deletDepartment(@RequestParam("id") String id) throws DingYunDepartException {
        return departmentService.deleteDepartRecur(id);
    }
}
