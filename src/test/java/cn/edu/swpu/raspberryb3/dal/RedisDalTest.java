package cn.edu.swpu.raspberryb3.dal;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午6:15
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisDalTest {
//    @Autowired
//    private RedisDal redisDal;
//
//    private DHMessage dhMessage = createDHMessage();

    @Resource
    private StringRedisTemplate redisTemplate;
    @Test
    public void setValue() {



        redisTemplate.opsForValue().set("201631064212","first");
        System.out.println(redisTemplate.opsForValue().get("20163106421"));
    }

//    private DHMessage createDHMessage(){
//        DHMessage dhMessage = new DHMessage();
//        dhMessage.setTempetature("37");
//        dhMessage.setHumidity("140");
//        return dhMessage;
//    }
//
//    @Test
//    public void save() {
//        DHMessage dhMessageSave = redisDal.save(dhMessage);
//        Assert.assertNotNull(dhMessageSave);
//
//    }
//
//    @Test
//    public void save1() {
//    }
//
//    @Test
//    public void delete() {
//    }
//
//    @Test
//    public void getDHMessage() {
//    }
//
//    @Test
//    public void update() {
//    }
}