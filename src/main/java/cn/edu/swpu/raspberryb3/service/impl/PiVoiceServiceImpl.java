package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.service.PiBaseService;
import cn.edu.swpu.raspberryb3.service.PiVoiceService;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: 束手就擒
 * @Date: 19-7-10 下午6:27
 * @Description:
 * 有效信号为低电平
 */
@Slf4j
@Service
public class PiVoiceServiceImpl implements PiVoiceService {

    private GpioPinDigitalInput voiceSenser;

    private PiBaseService piBaseService;

    @Autowired
    public PiVoiceServiceImpl(Map<String,GpioPin> gpioPinMap,
                              PiBaseService piBaseService){
        this.voiceSenser = (GpioPinDigitalInput) gpioPinMap.get("voiceSenser");
        this.piBaseService = piBaseService;
    }
    @Override
    public void getEdge() {

        //去抖动
        voiceSenser.setDebounce(200);
        voiceSenser.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                log.info(event.getPin()+ " : " + event.getState());
                piBaseService.turnOnSecond();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                piBaseService.turnOffSecond();
            }
        });
    }

}
