package cn.edu.swpu.raspberryb3.dal;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import cn.edu.swpu.raspberryb3.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:09
 * @Description:
 * 将DHMessage(温湿度传感器记录)放在list里面，每次leftPop()即可以得到最新的消息
 */
@Component
public class RedisDal {

    @Autowired
    private RedisTemplate<String,DHMessage> redisTemplate;



    /**
     * @param dhMessage
     * @return
     */
    public DHMessage save(DHMessage dhMessage){
        String key = KeyUtil.getRedisKey();
        redisTemplate.opsForList().leftPush(key,dhMessage);
        return getDHMessage(key);
    }

    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public DHMessage getDHMessage(String key){
        return redisTemplate.opsForList().leftPop(key);
    }
}
