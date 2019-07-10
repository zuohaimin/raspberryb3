package cn.edu.swpu.raspberryb3.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:13
 * @Description:
 */
public class KeyUtil {

    public static synchronized String genDHMessageTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        return formatter.format(LocalDateTime.now());
    }

    public static synchronized String getRedisKey(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH");
        return formatter.format(LocalDateTime.now());
    }
}
