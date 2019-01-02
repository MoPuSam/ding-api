package com.oulam.dingding.controller;

import com.oulam.dingding.service.DingBizService;
import com.oulam.dingding.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务回调接口相关控制器
 */
@Controller
@RequestMapping(value = "/ding/biz")
public class BizController {
    @Autowired
    DingBizService dingBizService;

    @ResponseBody
    @RequestMapping(value = "/corp_biz_callback/add", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int registBizInterface(HttpServletRequest request){//注册业务事件回调接口
        Map<String,Object> params = new HashMap<>();
        String[] call_back_tag = request.getParameterValues("call_back_tag");
        String biz_url = request.getParameter("biz_url");
        String token = "oulam3316";
        String aes_key = Utils.getRandomStr(43);
        params.put("call_back_tag",call_back_tag);
        params.put("token",token);
        params.put("aes_key",aes_key);
        params.put("url",biz_url);
        return dingBizService.registBizInterface(params);
    }
    @ResponseBody
    @RequestMapping(value = "/corp_biz_callback/del", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public int delBizInterface(HttpServletRequest request){//删除事件回调接口
        return dingBizService.deleteCallBack();
    }
    @ResponseBody
    @RequestMapping(value = "/corp_biz_callback/get", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public String getCallBack(HttpServletRequest request){//查询事件回调接口
        return dingBizService.getCallBack();
    }
}
