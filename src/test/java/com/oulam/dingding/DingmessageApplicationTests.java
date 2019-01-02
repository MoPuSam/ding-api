package com.oulam.dingding;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.oulam.dingding.utils.BaiduText2Audio;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DingmessageApplication.class)
public class DingmessageApplicationTests {
	@Autowired
	BaiduText2Audio bta;
	@Test
	public void T2A(){
		bta.getAudio("测试一下");
	}

}
