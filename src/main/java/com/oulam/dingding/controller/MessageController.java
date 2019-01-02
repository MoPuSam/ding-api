package com.oulam.dingding.controller;

import com.oulam.dingding.exception.DingYunMessageException;
import com.oulam.dingding.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 *消息服务相关(发送语音消息,发送链接消息,发送markdown消息,发送文件消息)
 */
@Controller
@RequestMapping(value = "/ding/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Value("${file.path}")
    private String filePath;
    /**
     * 发送语音消息
     * @param :消息内容message,接收的用户钉钉userid
     * @return 返回码
     */
    @ResponseBody
    @RequestMapping(value = "/voice", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int getVoiceMessage(HttpServletRequest request) throws DingYunMessageException {
        String message = request.getParameter("message");
        String userid = request.getParameter("userid");
        return messageService.sendVoiceMessage(message,userid,null,false);
    }
    /**
     * 发送链接消息
     * @param :接收的用户钉钉userid,文字的链接地址messageUrl,图片的链接地址picUrl,标题title,文字内容text
     * @return 返回码
     */
    @ResponseBody
    @RequestMapping(value = "/link", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int getLinkMessage(HttpServletRequest request) throws DingYunMessageException {
        String userid = request.getParameter("userid");
        HashMap<String,String> params = new HashMap<>();
        String messageUrl = request.getParameter("messageUrl");
        String picUrl = request.getParameter("picUrl");
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        params.put("messageUrl",messageUrl);
        params.put("picUrl",picUrl);
        params.put("title",title);
        params.put("text",text);
        return messageService.sendLinkMessage(params,userid,null,false);
    }
    /**
     * 发送markdown消息
     * @param :接收的用户钉钉userid,标题title,文字内容text
     * @return 返回码
     */
    @ResponseBody
    @RequestMapping(value = "/markdown", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public int getMarkDownMessage(HttpServletRequest request) throws DingYunMessageException {
        String userid = request.getParameter("userid");
        HashMap<String,String> params = new HashMap<>();
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        params.put("title",title);
        //"# 这是支持markdown的文本 \n## 标题2  \n* 列表1 \n![alt 啊](https://gw.alipayobjects.com/zos/skylark-tools/public/files/b424a1af2f0766f39d4a7df52ebe0083.png)"
        params.put("text",text);
        return messageService.sendMarkDownMessage(params,userid,null,false);
    }
    /**
     * 发送文件消息
     * @param :接收的用户钉钉userid,文件类型filetype,文件表单元素名file
     * @return 返回码
     */
    @ResponseBody
    @RequestMapping(value = "/file", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String getPictureMessage(HttpServletRequest request) throws UnsupportedEncodingException {
        MultipartHttpServletRequest params = (MultipartHttpServletRequest)request;
        String userid = params.getParameter("userid");
        String filetype = params.getParameter("filetype");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)) {
            List<MultipartFile> files = params.getFiles("file");
            MultipartFile file = null;
            if(files.size()>0) {
                for (int i = 0; i < files.size(); ++i) {
                    file = files.get(i);
                    //String filename = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
                    //File fileparam = new File(filePath, filename);
                    File fileparam = new File(filePath, file.getOriginalFilename());
                    if (!file.isEmpty()) {
                        try {
                            file.transferTo(fileparam);
                            messageService.sendFileMessage(fileparam,filetype,userid,null,false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return  "上传失败"+i;
                        }
                    } else {
                        return "上传成功"+i;
                    }
                }
            }
        }
        return "发送成功";
    }

}
