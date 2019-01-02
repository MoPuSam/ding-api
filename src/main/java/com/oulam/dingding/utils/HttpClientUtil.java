package com.oulam.dingding.utils;

import com.alibaba.fastjson.JSONObject;
import com.oulam.dingding.service.RoleService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求服务工具类
 */
@Service
public class HttpClientUtil {
    protected static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    @Value("${com.oulam.accessTokenUrl}")
    private String accessTokenurl;
    @Value("${com.oulam.corpId}")
    private String corpid;
    @Value("${com.oulam.corpSecret}")
    private String corpsecret;


    private static String accessTokenUrl;

    private static String corpId;

    private static String corpSecret;

    private static String access_Token;

    @PostConstruct
    public void init(){
        accessTokenUrl = accessTokenurl;
        corpId = corpid;
        corpSecret = corpsecret;
        if(access_Token==null||"".equals(access_Token)){
            Map<String,Object> map = new HashMap<>();
            map.put("corpid",corpId);
            map.put("corpsecret",corpSecret);
            String access_token_str = httpGetStringResult(accessTokenUrl, map);//获取access_token;
            JSONObject jsonStr = JSONObject.parseObject(access_token_str); //转json结构
            access_Token = jsonStr.getString("access_token");//获取access_Token
            log.info("access_Token="+access_Token);
        }
    }

    public static String getAccessToken() {
        return access_Token;
    }

    public static String httpGetStringResult(String url,Map<String,Object> param){
        String content = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(param != null && !param.isEmpty()){
            StringBuffer strparams = new StringBuffer();
            for (Map.Entry<String, Object> map : param.entrySet()) {
                strparams.append(map.getKey()).append("=").append(map.getValue().toString()).append("&");
            }
            strparams = strparams.deleteCharAt(strparams.length()-1);
            url = url + "?" + strparams;
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity,"UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            try {
                if(null!=response){
                    response.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return content;
    }

    public static String doPost(String requestUrl,JSONObject json){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(requestUrl);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader("contentType",
                    "application/json"));
            post.setEntity(s);
            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            System.out.println(result);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                System.out.println("请求服务器成功，做相应处理");

            } else {
                System.out.println("请求服务端失败");
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * 发送get请求
     * @param url       链接地址
     * @param charset   字符编码，若为null则默认utf-8
     * @return
     */
    public static String doGet(String url,String charset){
        if(null == charset){
            charset = "utf-8";
        }
        CloseableHttpClient httpClient = null;
        HttpGet httpGet= null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @param file
     * @param url
     * @param params
     */
    public static String sendFileAndParamsByHttpPost(File file, String url, Map<String, String> params) {
        CloseableHttpClient client = HttpClients.custom().build();
        String result = "";
        try {
            HttpPost post = new HttpPost(url);
            FileBody fileBody = new FileBody(file);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
//				multipartEntityBuilder.addTextBody(key, value);
                //防止乱码
                multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset("utf-8"));
            }
            //addPart文件流的参数名
            HttpEntity entity = multipartEntityBuilder
                    .addPart("media", fileBody)
                    .build();
            post.setEntity(entity);
            CloseableHttpResponse resp = client.execute(post);
            // 获取响应输入流
            InputStream inStream = resp.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            System.out.println(result);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            } else {
                System.out.println("请求服务端失败");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
