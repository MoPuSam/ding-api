package com.oulam.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.ExtLinkMan;
import com.oulam.dingding.bean.Leave_Employer;
import com.oulam.dingding.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 外部联系人相关服务
 */
@Service
public class ExtLinkManService {
    protected static final Logger log = LoggerFactory.getLogger(ExtLinkManService.class);

    /**
     * 获取外部联系人列表
     * @param size
     * @param offset
     * @return
     */
    public List<ExtLinkMan> getExtLinkManList(int size, int offset){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/extcontact/list?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("size",size);
        jsonObject.put("offset",offset);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("外部联系人列表==========>"+jsonstr);
        JSONObject json = JSON.parseObject(jsonstr);
        List<ExtLinkMan> elms = new ArrayList<>();
        int errcode = json.getInteger("errcode");
        if(errcode==0){
            JSONArray results = json.getJSONArray("results");
            ExtLinkMan elm = new ExtLinkMan();
            for(int i = 0;i<results.size();i++){
                JSONObject result = results.getJSONObject(i);
                elm.setTitle(result.getString("title"));
                JSONArray sd_ids = result.getJSONArray("share_dept_ids");
                int[] share_dept_ids = new int[sd_ids.size()];
                for(int j = 0;j<sd_ids.size();j++){
                    int share_dept_id = sd_ids.getInteger(j);
                    share_dept_ids[j] = share_dept_id;
                }
                elm.setShare_dept_ids(share_dept_ids);
                JSONArray labelids = result.getJSONArray("label_ids");
                int[] label_ids = new int[labelids.size()];
                for(int j = 0;j<labelids.size();j++){
                    int label_id = labelids.getInteger(j);
                    label_ids[j] = label_id;
                }
                elm.setLabel_ids(label_ids);
                elm.setRemark(result.getString("remark"));
                elm.setAddress(result.getString("address"));
                elm.setName(result.getString("name"));
                elm.setFollower_user_id(result.getString("follower_user_id"));
                elm.setState_code(result.getString("state_code"));
                elm.setCompany_name(result.getString("company_name"));
                JSONArray su_ids = result.getJSONArray("share_user_ids");
                String[] share_user_ids = new String[su_ids.size()];
                for(int j = 0;j<su_ids.size();j++){
                    String share_user_id = su_ids.getString(j);
                    share_user_ids[j] = share_user_id;
                }
                elm.setShare_user_ids(share_user_ids);
                elm.setMobile(result.getString("mobile"));
                elm.setUser_id(result.getString("userid"));
                elms.add(elm);
            }
        }
        return elms;
    }
    /**
     * 获取企业外部联系人详情
     * @param user_id
     * @return
     */
    public ExtLinkMan getExtLinkManByID(String user_id){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/extcontact/get?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id",user_id);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        ExtLinkMan elm = new ExtLinkMan();
        JSONObject json = JSONObject.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        if(errcode==0) {
            JSONObject result = json.getJSONObject("result");
            elm.setTitle(result.getString("title"));
            JSONArray sd_ids = result.getJSONArray("share_dept_ids");
            int[] share_dept_ids = new int[sd_ids.size()];
            for (int j = 0; j < sd_ids.size(); j++) {
                int share_dept_id = sd_ids.getInteger(j);
                share_dept_ids[j] = share_dept_id;
            }
            elm.setShare_dept_ids(share_dept_ids);
            JSONArray labelids = result.getJSONArray("label_ids");
            int[] label_ids = new int[labelids.size()];
            for (int j = 0; j < labelids.size(); j++) {
                int label_id = labelids.getInteger(j);
                label_ids[j] = label_id;
            }
            elm.setLabel_ids(label_ids);
            elm.setRemark(result.getString("remark"));
            elm.setAddress(result.getString("address"));
            elm.setName(result.getString("name"));
            elm.setFollower_user_id(result.getString("follower_user_id"));
            elm.setState_code(result.getString("state_code"));
            elm.setCompany_name(result.getString("company_name"));
            JSONArray su_ids = result.getJSONArray("share_user_ids");
            String[] share_user_ids = new String[su_ids.size()];
            for (int j = 0; j < su_ids.size(); j++) {
                String share_user_id = su_ids.getString(j);
                share_user_ids[j] = share_user_id;
            }
            elm.setShare_user_ids(share_user_ids);
            elm.setMobile(result.getString("mobile"));
            elm.setUser_id(result.getString("userid"));
        }
        return elm;
    }
    /**
     * 添加外部联系人
     * @param param
     * @return
     */
    public String addExtLinkMan(Map<String,Object> param){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/extcontact/create?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        ExtLinkMan elm = new ExtLinkMan();
        elm.setTitle((String) param.get("title"));
        elm.setShare_dept_ids((int[]) param.get("share_dept_ids"));
        elm.setLabel_ids((int[]) param.get("label_ids"));
        elm.setRemark((String) param.get("remark"));
        elm.setAddress((String) param.get("address"));
        elm.setName((String) param.get("name"));
        elm.setFollower_user_id((String) param.get("follower_user_id"));
        elm.setState_code((String) param.get("state_code"));
        elm.setCompany_name((String) param.get("company_name"));
        elm.setShare_user_ids((String[]) param.get("share_user_ids"));
        elm.setMobile((String) param.get("mobile"));
        elm.setUser_id((String) param.get("userid"));
        jsonObject.put("contact",elm);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        String userid = json.getString("userid");
        return userid;
    }
    //删除外部联系人
    public int delExtLinkManByID(String user_id){
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/extcontact/delete?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id",user_id);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        log.info("外部联系人删除结果==========>"+jsonstr);
        JSONObject json = JSON.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        return errcode;
    }
    /**
     * 更新外部联系人
     * @param params
     * @return
     */
    public int updateExtLinkMan(Map<String, Object> params) {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingurl = "https://oapi.dingtalk.com/topapi/extcontact/update?access_token="+access_Token;
        JSONObject jsonObject = new JSONObject();
        ExtLinkMan elm = new ExtLinkMan();
        elm.setUser_id((String) params.get("user_id"));
        elm.setTitle((String) params.get("title"));
        elm.setShare_dept_ids((int[]) params.get("share_dept_ids"));
        elm.setLabel_ids((int[]) params.get("label_ids"));
        elm.setRemark((String) params.get("remark"));
        elm.setAddress((String) params.get("address"));
        elm.setName((String) params.get("name"));
        elm.setFollower_user_id((String) params.get("follower_user_id"));
        elm.setState_code((String) params.get("state_code"));
        elm.setCompany_name((String) params.get("company_name"));
        elm.setShare_user_ids((String[]) params.get("share_user_ids"));
        elm.setMobile((String) params.get("mobile"));
        elm.setUser_id((String) params.get("user_id"));
        jsonObject.put("contact",elm);
        String jsonstr = HttpClientUtil.doPost(dingDingurl,jsonObject);
        JSONObject json = JSON.parseObject(jsonstr);
        int errcode = json.getInteger("errcode");
        return errcode;
    }
}
