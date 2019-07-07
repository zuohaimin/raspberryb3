package cn.edu.swpu.raspberryb3.utils;

import cn.edu.swpu.raspberryb3.constant.RaspberryConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: 束手就擒
 * @Date: 19-7-7 下午4:43
 * @Description:
 * 发送短信
 */
public class SmsSender {

    private final static String URL = "http://api01.monyun.cn:7901/sms/v2/std/single_send";

    private final static String APIKEY = "79d877f035bb25aadd5b2fa6174d9ceb";

    /**
     *
     * @param content
     * @return
     * 参数拼接：{"apikey":"abade5589e2798f82f006bbc36d269ce","mobile":"138xxxxxxxx","content":"%d1%e9%d6%a"}
     */
    private String getQueryArgs(String content){
        String contentEncode = null;
        try {
             contentEncode = URLEncoder.encode(content,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"apikey\":\"");
        sb.append(APIKEY);
        sb.append("\",\"mobile\":\"");
        sb.append(RaspberryConstant.MOBILE_NUM);
        sb.append("\",\"content\":\"");
        sb.append(contentEncode);
        sb.append("\"}");
        return sb.toString();
    }


    public static void main(String[] args) {
        SmsSender smsSender = new SmsSender();
        System.out.println(smsSender.getQueryArgs("你好"));
    }
}
