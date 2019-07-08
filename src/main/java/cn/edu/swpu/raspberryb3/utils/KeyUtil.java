package cn.edu.swpu.raspberryb3.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:13
 * @Description:
 */
public class KeyUtil {

    public static synchronized String genRedisKey(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(LocalDateTime.now());
    }

}
