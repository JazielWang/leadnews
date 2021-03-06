package com.jaziel.admin;

import com.jaziel.common.aliyun.AliyunImageScanRequest;
import com.jaziel.common.aliyun.AliyunTextScanRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/7/21 16:09
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AliyunTest {
    @Autowired
    private AliyunTextScanRequest aliyunTextScanRequest;

    @Test
    public void testTextScanRequest() throws Exception {
        String message = "阿里云，毒品交易阿里巴巴集团旗下云计算品牌，全球卓越的云计算技术和服务提供商。创立于2009年，在杭州、北京、硅谷等地设有研发中心和运营机构。";
        String response = aliyunTextScanRequest.textScanRequest(message);
        System.out.println(response + "--------------------------");
    }

    @Autowired
    private AliyunImageScanRequest aliyunImageScanRequest;
    @Test
    public void testImageScanRequest(){
        try {
            List list = new ArrayList<>();
            list.add("http://47.94.7.85/group1/M00/00/00/rBENvl02ZtKAEgFqAACNdiGk7IM981.jpg"
            );
            String response = aliyunImageScanRequest.imageScanRequest(list);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
