package com.oulam.dingding.controller;

import com.oulam.dingding.service.FileService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *媒体文件上传相关(上传媒体文件)
 */
@Controller
@RequestMapping(value = "/ding/file")
public class MediaController {
    @Value("${file.path}")
    private String filePath;
    @Autowired
    private FileService fileService;
    /**
     * 获取企业下的自定义空间
     */
    @ResponseBody
    @RequestMapping(value = "/file/space", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public String getCustomSpace(){
        return fileService.getCustomSpace();
    }
    /**
     * 上传媒体文件
     */
    @ResponseBody
    @RequestMapping(value = "/file/media", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String uploadMedia(HttpServletRequest request){
        MultipartHttpServletRequest params = (MultipartHttpServletRequest)request;
        String type = params.getParameter("type");
        //String media = params.getParameter("media");
        Map map = new HashMap();
        map.put("type",type);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)) {
            List<MultipartFile> files = params.getFiles("media");
            MultipartFile file = null;
            if(files.size()>0) {
                for (int i = 0; i < files.size(); ++i) {
                    file = files.get(i);
                    File fileparam = new File(filePath, file.getOriginalFilename());
                    if (!file.isEmpty()) {
                        try {
                            file.transferTo(fileparam);
                            String media_id = fileService.uploadMedia(fileparam, map);

                        } catch (Exception e) {
                            e.printStackTrace();
                            return "You failed to upload " + i + " => "
                                    + e.getMessage();
                        }
                    } else {
                        return "You failed to upload " + i
                                + " because the file was empty.";
                    }
                }
            }
        }
        return "上传文件成功";
    }

    /**
     * 将文件上传给用户（未成功，返回不合法的agentid，需要E应用支持）
     * @param :接收文件的userid,文件类型type,表单元素name media
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file/send", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public String sendFileToUser(HttpServletRequest request){//返回不合法的agentid
        MultipartHttpServletRequest params = (MultipartHttpServletRequest)request;
        String userid = params.getParameter("userid");
        String type = params.getParameter("type");
        Map map = new HashMap();
        map.put("type",type);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)) {
            List<MultipartFile> files = params.getFiles("media");
            MultipartFile file = null;
            if(files.size()>0) {
                for (int i = 0; i < files.size(); ++i) {
                    file = files.get(i);
                    File fileparam = new File(filePath, file.getOriginalFilename());
                    if (!file.isEmpty()) {
                        try {
                            file.transferTo(fileparam);
                            String media_id = fileService.uploadMedia(fileparam, map);
                            fileService.sendFileToUser(userid,media_id,file.getOriginalFilename());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "You failed to upload " + i + " => "
                                    + e.getMessage();
                        }
                    } else {
                        return "You failed to upload " + i
                                + " because the file was empty.";
                    }
                }
            }
        }
        return "文件发送用户成功";
    }
}
