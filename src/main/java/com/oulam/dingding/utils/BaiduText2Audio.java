package com.oulam.dingding.utils;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.oulam.dingding.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * 百度文字转语音服务
 */
@Service
public class BaiduText2Audio {
    protected static final Logger log = LoggerFactory.getLogger(BaiduText2Audio.class);
    //id
    private static String appid ;
    //密钥
    private static String appkey ;
    //secret
    private static String secret ;

    private static String ffmpegWinPath;

    private static String ffmpegLinuxPath;

    @Value("${file.path}")
    private String filePath;
    @Value("${com.oulam.baiduyun.appid}")
    private String c_appid ;
    @Value("${com.oulam.baiduyun.appkey}")
    private String c_appkey ;
    @Value("${com.oulam.baiduyun.secret}")
    private String c_secret ;
    @Value("${com.oulam.fmmpeg.win}")
    private String c_ffmpegWinPath;
    @Value("${com.oulam.fmmpeg.linux}")
    private String c_ffmpegLinuxPath;

    private static AipSpeech client;

    @PostConstruct
    public void init() {
        appid = c_appid;
        appkey = c_appkey;
        secret = c_secret;
        ffmpegWinPath = c_ffmpegWinPath;
        ffmpegLinuxPath = c_ffmpegLinuxPath;
    }

    private BaiduText2Audio() {}

    public static synchronized AipSpeech getAipSpeechClient(){
        if(client==null) {
            client = new AipSpeech(appid, appkey, secret);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
        }
        return client;
    }
    /**
     * 获取语音文件
     * @param text
     * @return
     */
    public File getAudio(String text){
        AipSpeech client = getAipSpeechClient();
        // 调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "5");
        options.put("pit", "5");
        options.put("per", "4");
        TtsResponse res = client.synthesis(text, "zh", 1, options);
        byte[] data = res.getData();
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        Date date = new Date();
        String fileName = date.getTime()+".mp3";
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                File dir = new File(filePath);
                if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                    dir.mkdirs();
                }
                file = new File(filePath+fileName);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        if (res1 != null) {
            log.info(res1.toString(2));
        }
        return file;
    }
    /**
     * 将amr文件输入转为mp3格式
     * @param file
     * @return
     */
    public static InputStream amrToMP3(MultipartFile file) {
        String ffmpegPath = getLinuxOrWindowsFfmpegPath();
        Runtime runtime = Runtime.getRuntime();
        try {
            String filePath = copyFile(file.getInputStream(), file.getOriginalFilename());

            String substring = filePath.substring(0, filePath.lastIndexOf("."));

            String amrFilePath = substring + ".amr";

            //执行ffmpeg文件，将mp3格式转为amr
            //filePath ----> amr文件在临时文件夹中的地址
            //mp3FilePath  ----> 转换后的mp3文件地址
            /**
             * -ab(-b:a) bitrate 设置音频码率
             * -ar freq 设置音频采样率
             * -c:a(-acodec) 设置声音解码器
             * -y 覆盖输出文件，若果源文件已经存在，那么不经提示，直接覆盖
             * -vol 音量大小
             * -ac (声道数1和2)
             * @param sourcePath 源文件地址,输出位置:源文件文件夹内,只改变文件格式
             */
            Process p = runtime.exec(ffmpegPath + "ffmpeg -i " + filePath + " -c:a libopencore_amrnb -ac 1 -ar 8000 -b:a 7.95K -y " + amrFilePath);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame

            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();

            File amrFile = new File(amrFilePath);
            InputStream fileInputStream = new FileInputStream(amrFile);

            //应该在调用该方法的地方关闭该input流（使用完后），并且要删除掉临时文件夹下的相应文件
            /*File amrFile = new File(filePath);
            File mp3File = new File(mp3FilePath);
            if (amrFile.exists()) {
                boolean delete = amrFile.delete();
                System.out.println("删除源文件："+delete);
            }
            if (mp3File.exists()) {
                boolean delete = mp3File.delete();
                System.out.println("删除mp3文件："+delete);
            }*/

            return fileInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            runtime.freeMemory();
        }
        return null;
    }

    /**
     * 将mp3文件输入流转为amr格式
     * @param inputStream  amr文件的输入流（也可以是其它的文件流）
     * @param fileName  文件名（包含后缀）
     * @return
     */
    public static InputStream amrToMP3(InputStream inputStream, String fileName) {
        String ffmpegPath = getLinuxOrWindowsFfmpegPath();
        Runtime runtime = Runtime.getRuntime();
        try {
            String filePath = copyFile(inputStream, fileName);
            String substring = filePath.substring(0, filePath.lastIndexOf("."));
            String amrFilePath = substring + ".amr";

            //执行ffmpeg文件，将mp3格式转为amr
            //filePath ----> mp3文件在临时文件夹中的地址
            //mp3FilePath  ----> 转换后的amr文件地址
            Process p = runtime.exec(ffmpegPath + "ffmpeg -i" + " " +filePath + " " + amrFilePath);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame

            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();

            File file = new File(amrFilePath);
            InputStream fileInputStream = new FileInputStream(file);

            //应该在调用该方法的地方关闭该input流（使用完后），并且要删除掉临时文件夹下的相应文件
            /*File amrFile = new File(filePath);
            File mp3File = new File(mp3FilePath);
            if (amrFile.exists()) {
                boolean delete = amrFile.delete();
                System.out.println("删除源文件："+delete);
            }
            if (mp3File.exists()) {
                boolean delete = mp3File.delete();
                System.out.println("删除mp3文件："+delete);
            }*/
            return fileInputStream;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            runtime.freeMemory();
        }
        return null;
    }

    /**
     * 将用户输入的amr音频文件流转为音频文件并存入临时文件夹中
     * @param inputStream  输入流
     * @param fileName  文件姓名
     * @return  amr临时文件存放地址
     * @throws IOException
     */
    private static String copyFile(InputStream inputStream, String fileName) throws IOException {
        Properties props = System.getProperties();
        String filePath = props.getProperty("user.home") + File.separator + "AMRTempFile"; //创建临时目录
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String outPutFile = dir + File.separator + fileName;

        OutputStream outputStream = new FileOutputStream(outPutFile);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return outPutFile;
    }

    /**
     * 判断系统是Windows还是linux并且拼接ffmpegPath
     * @return
     */
    private static String getLinuxOrWindowsFfmpegPath() {
        String ffmpegPath = "";
        String osName = System.getProperties().getProperty("os.name");
        if (osName.toLowerCase().indexOf("linux") >= 0) {
            ffmpegPath = ffmpegLinuxPath;
        } else {
            ffmpegPath = ffmpegWinPath;
        }
        return ffmpegPath;
    }
}
