package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.dao.DHMessageMapper;
import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import cn.edu.swpu.raspberryb3.sensor.DHT11;
import cn.edu.swpu.raspberryb3.service.DHMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
class DHMessageServiceImpl implements DHMessageService {

    @Autowired
    private DHMessageMapper dhMessageMapper;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    private DHT11 dht11;

    /**
     * 获取采集的温度数据
     * @return
     */
    @Override
    public DHMessage getDHMessage() {
        return dhMessageMapper.queryTopOne();
    }

    @Override
    public boolean collectDHInformation() {
        if (!(atomicInteger.get() == 0)) {
            return false;
        }
        atomicInteger.getAndIncrement();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Runtime.getRuntime().exec("sudo python test.py");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return true;
    }
}
