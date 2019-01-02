package com.oulam.dingding.service;

import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.bean.Y_Message;
import com.oulam.dingding.dao.UserMapper;
import com.oulam.dingding.dao.Y_MessageMapper;
import com.oulam.dingding.exception.DingYunMessageException;
import com.oulam.dingding.utils.BaiduText2Audio;
import com.oulam.dingding.utils.HttpClientUtil;
import org.apache.http.entity.ContentType;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息相关服务
 */
@Service(value = "messageService")
public class MessageService {
    protected static final Logger log = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    private Y_MessageMapper mapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentService departMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private BaiduText2Audio btaService;
    @Value("${com.oulam.agentId}")
    private String agentId;

    /**
     * 消息发送完毕f9置0 查询数据库中未完成的工作消息推送
     */
    public void MessageTasks() throws DingYunMessageException {
        List<Y_Message> messageQueue = mapper.getMessageNotFinish();
        if(messageQueue.size()==0){
            return;
        }
        List<Y_Message> messageFinished = new ArrayList<>();
        for(Y_Message message:messageQueue){
            String userid = message.getF2();
            String depart = message.getF3();
            boolean alluser = message.getF4();
            String content = message.getF5();
            int taskId = sendMessage(content,userid,depart,alluser);
            int status = messageProcess(Long.valueOf(agentId),taskId);//任务执行状态，0=未开始，1=处理中，2=处理完毕
            if(status == 2){//如果返回结果显示已经完成则云表内显示已完成
                message.setF9(true);
                messageFinished.add(message);
            }
        }
        mapper.updateBatchByPrimaryKey(messageFinished);
    }

    /**
     * 发送工作通知
     * @param message
     * @param userid
     * @param dptid
     * @param alluser
     * @return
     */
    public int sendMessage(String message,String userid,String dptid,boolean alluser) throws DingYunMessageException {
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("userid_list", userid);
        mapParam.put("to_all_user",alluser);
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token="+access_Token;
        //消息内容
        JSONObject text = new JSONObject();
        text.put("content",message);
        JSONObject msg = new JSONObject();
        msg.put("msgtype","text");
        msg.put("text",text);
        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",agentId);
        jsonObject.put("userid_list",mapParam.get("userid_list"));
        jsonObject.put("msg",msg);
        jsonObject.put("to_all_user",mapParam.get("to_all_user"));
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMessageException(errcode);
        }
        //返回的任务id
        int task_id = firstJson.getInteger("task_id");
        return task_id;
    }

    /**
     * 获取工作通知消息的发送结果
     * @param agendId
     * @param taskId
     * @return
     */
    public int messageProcess(long agendId ,long taskId) throws DingYunMessageException {
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress?access_token="+access_Token;
        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",agendId);
        jsonObject.put("task_id",taskId);
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        String progress = firstJson.getString("progress");
        JSONObject secJson = JSONObject.parseObject(progress);
        int status = secJson.getInteger("status");
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMessageException(errcode);
        }
        return status;
    }

    /**
     * 发送图片工作通知
     * @param pic
     * @param filetype
     * @param userid
     * @param dptid
     * @param alluser
     * @return
     */
    public int sendFileMessage(File pic,String filetype, String userid,String dptid,boolean alluser) throws DingYunMessageException {
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("userid_list", userid);
        mapParam.put("to_all_user",alluser);
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token="+access_Token;
        //消息内容
        JSONObject file = new JSONObject();
        Map<String,String> params = new HashMap<>();
        params.put("type",filetype);
        String media_id = fileService.uploadMedia(pic,params);
        file.put("media_id",media_id);
        JSONObject msg = new JSONObject();
        msg.put("msgtype",filetype);
        msg.put(filetype,file);
        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",agentId);
        jsonObject.put("userid_list",mapParam.get("userid_list"));
        jsonObject.put("msg",msg);
        jsonObject.put("to_all_user",mapParam.get("to_all_user"));
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMessageException(errcode);
        }
        //返回的任务id
        int task_id = firstJson.getInteger("task_id");
        return task_id;
    }
    /**
     * 发送语音工作通知
     * @param message
     * @param userid
     * @param dptid
     * @param alluser
     * @return
     */
    public int sendVoiceMessage(String message,String userid,String dptid,boolean alluser) throws DingYunMessageException {
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("userid_list", userid);
        mapParam.put("to_all_user",alluser);
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token="+access_Token;
        //消息内容
        JSONObject voice = new JSONObject();
        //获取从百度转化而来的语音文件
        File audio = btaService.getAudio(message);
        Map<String,String> params = new HashMap<>();
        params.put("type","voice");
        //获取语音时长
        /**
        long duration = -1;
        Encoder encoder = new Encoder();
        try {
            MultimediaInfo m = encoder.getInfo(audio);
            long ls = m.getDuration();
            log.info("此音频时长为:" + ls / 60000 + "分" + (ls%60000) / 1000 + "秒！");
            duration = (ls%60000) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }**/

        int duration = 0;
        try {
            MP3File f = (MP3File) AudioFileIO.read(audio);
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
            duration = audioHeader.getTrackLength();
        } catch(Exception e) {
            log.info("获取语音时长失败!");
            e.printStackTrace();
        }
         //mp3转amr https://blog.csdn.net/u010018421/article/details/71280099
        FileInputStream fileInputStream = null;
        MultipartFile multipartFile = null;
        try {
            fileInputStream = new FileInputStream(audio);
            multipartFile = new MockMultipartFile(audio.getName(), audio.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath = audio.getAbsolutePath();
        String substring = filePath.substring(0, filePath.lastIndexOf("."));
        String amrFilePath = substring + ".amr";

        File file = new File(amrFilePath);
        InputStream in = BaiduText2Audio.amrToMP3(multipartFile);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String media_id = fileService.uploadMedia(audio,params);
        voice.put("media_id",media_id);
        voice.put("duration",""+duration);
        JSONObject msg = new JSONObject();
        msg.put("msgtype","voice");
        msg.put("voice",voice);
        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",agentId);
        jsonObject.put("userid_list",mapParam.get("userid_list"));
        jsonObject.put("msg",msg);
        jsonObject.put("to_all_user",mapParam.get("to_all_user"));
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMessageException(errcode);
        }
        //返回的任务id
        int task_id = firstJson.getInteger("task_id");
        return task_id;
    }

    /**
     * 发送链接消息
     * @param params
     * @param userid
     * @param dptid
     * @param alluser
     * @return
     */
    public int sendLinkMessage(Map params,String userid,String dptid,boolean alluser) throws DingYunMessageException {
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("userid_list", userid);
        mapParam.put("to_all_user",alluser);
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token="+access_Token;
        //消息内容
        JSONObject link = new JSONObject();
        link.put("messageUrl",params.get("messageUrl"));
        link.put("picUrl",params.get("picUrl"));
        link.put("title",params.get("title"));
        link.put("text",params.get("text"));
        JSONObject msg = new JSONObject();
        msg.put("msgtype","link");
        msg.put("link",link);
        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",agentId);
        jsonObject.put("userid_list",mapParam.get("userid_list"));
        jsonObject.put("msg",msg);
        jsonObject.put("to_all_user",mapParam.get("to_all_user"));
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMessageException(errcode);
        }
        //返回的任务id
        int task_id = firstJson.getInteger("task_id");
        return task_id;
    }

    /**
     * 发送markdown消息
     * @param params
     * @param userid
     * @param dptid
     * @param alluser
     * @return
     */
    public int sendMarkDownMessage(Map params,String userid,String dptid,boolean alluser) throws DingYunMessageException {
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("userid_list", userid);
        mapParam.put("to_all_user",alluser);
        String access_Token = HttpClientUtil.getAccessToken();
        String dingDingAttendance = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token="+access_Token;
        //消息内容
        JSONObject markdown = new JSONObject();
        markdown.put("title",params.get("title"));
        markdown.put("text",params.get("text"));
        JSONObject msg = new JSONObject();
        msg.put("msgtype","markdown");
        msg.put("markdown",markdown);
        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agent_id",agentId);
        jsonObject.put("userid_list",mapParam.get("userid_list"));
        jsonObject.put("msg",msg);
        jsonObject.put("to_all_user",mapParam.get("to_all_user"));
        String jsonstr = HttpClientUtil.doPost(dingDingAttendance,jsonObject);
        JSONObject firstJson = JSONObject.parseObject(jsonstr);
        int errcode = firstJson.getInteger("errcode");
        if(errcode!=0){
            throw new DingYunMessageException(errcode);
        }
        //返回的任务id
        int task_id = firstJson.getInteger("task_id");
        return task_id;
    }
}