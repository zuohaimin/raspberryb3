package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.service.PiVoiceService;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Author: 束手就擒
 * @Date: 19-7-10 下午6:27
 * @Description:
 */
@Slf4j
public class PiVoiceServiceImpl implements PiVoiceService {

    private GpioPinDigitalInput voiceSenser;

    private GpioPinDigitalOutput ledSecond;

    @Autowired
    public PiVoiceServiceImpl(Map<String,RaspiPin> gpioPinMap){
        this.voiceSenser = (GpioPinDigitalInput) gpioPinMap.get("voiceSenser");
        this.ledSecond = (GpioPinDigitalOutput) gpioPinMap.get("ledSecond");
    }
    @Override
    public void getEdge() {

        //去抖动
        voiceSenser.setDebounce(200);
        voiceSenser.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                log.info(event.getPin()+ " : " + event.getState());
                ledSecond.high();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ledSecond.low();
            }
        });
        
    }
}
