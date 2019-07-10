package cn.edu.swpu.raspberryb3.utils;


import cn.edu.swpu.raspberryb3.constant.RaspberryConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: 束手就擒
 * @Date: 19-7-7 下午4:43
 * @Description:
 * 发送短信
 */
public class SmsSender {



    /**
     *
     * @return
     * 参数拼接：accountSid=7727bb0e0f9b910c48fc1ec5e3xxxxxx&to=186xxxxxxxx
     */
    private static String getQueryArgs(String accountSid, List<String> to, long timestamp, String sig,
                                       String templateid, String respDataType){
        StringBuilder sb = new StringBuilder();
        //accountSid,to,timestamp,sig
        sb.append("accountSid=").append(accountSid);
        sb.append("&to=");
        for (String s : to){
            sb.append(s).append(",");
        }
        //删除最后的一个英文逗号
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("&timestamp=").append(timestamp);
        sb.append("&sig=").append(sig);
        sb.append("&templateid=").append(templateid);
        sb.append("&respDataType=").append(respDataType);
        return sb.toString();
    }

    private static long getTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * @param sid
     * @param token
     * @param timestamp
     * @return
     * md5 加密
     */
    private static String getMd5(String sid, String token, long timestamp){
       StringBuilder sb = new StringBuilder();
       String source = sid+token+timestamp;

        try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] bytes = messageDigest.digest(source.getBytes());
                for (byte b : bytes){
                    String hex = Integer.toHexString(b&0xff);
                    if (hex.length() == 1){
                        sb.append("0").append(hex);
                    }else {
                        sb.append(hex);
                    }
                }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 谨慎调用，余额只有一个机会了，秒滴云
     * @param to
     * @return
     */
    public static String sendSms(List<String> to){
        long timestamp = getTimestamp();
        String sig = getMd5(RaspberryConstant.ACCOUNT_SID,RaspberryConstant.AUTH_TOKEN,timestamp);
        OutputStreamWriter outputStreamWriter;
        BufferedReader br;
        StringBuilder result = new StringBuilder();
        try {

            URL url = new URL(RaspberryConstant.SMS_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
            String args = getQueryArgs(RaspberryConstant.ACCOUNT_SID,to,timestamp,sig,RaspberryConstant.TEMPLATEID,RaspberryConstant.RESP_DATA_TYPE);
            System.out.println(args);
            outputStreamWriter.write(args);
            outputStreamWriter.flush();

            //读取返回的结果
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String temp = "";
            while ((temp = br.readLine()) !=null){
                result.append(temp);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();

    }
}
