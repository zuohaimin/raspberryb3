package cn.edu.swpu.raspberryb3.dal;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import cn.edu.swpu.raspberryb3.utils.KeyUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:09
 * @Description:
 */
@Component
public class RedisDal {

    @Autowired
    private RedisTemplate<String,DHMessage> redisTemplate;



    public DHMessage save(DHMessage dhMessage){
        String key = KeyUtil.genRedisKey();
        redisTemplate.opsForValue().set(key,dhMessage);
        return getDHMessage(key);
    }

    /**
     * 保存一个会过期的记录
     * @param dhMessage
     * @param time
     * @return
     */
    public DHMessage save(DHMessage dhMessage, long time){
        String key = KeyUtil.genRedisKey();
        redisTemplate.opsForValue().set(key,dhMessage,time, TimeUnit.SECONDS);
        return getDHMessage(key);
    }

    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public DHMessage getDHMessage(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public DHMessage update(String key, DHMessage dhMessage){
        redisTemplate.opsForValue().set(key,dhMessage);
        return getDHMessage(key);
    }


}
