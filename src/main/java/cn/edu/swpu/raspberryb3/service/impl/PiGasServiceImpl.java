package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.service.PiBaseService;
import cn.edu.swpu.raspberryb3.service.PiGasService;
import cn.edu.swpu.raspberryb3.utils.SmsSender;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 束手就擒
 * @Date: 19-7-7 下午10:48
 * @Description:
 */
@Slf4j
@Service
public class PiGasServiceImpl implements PiGasService{
    private GpioPinDigitalInput digitalInput;

    @Value("${to}")
    private List<String> to;

    private PiBaseService piBaseService;

    @Autowired
    public PiGasServiceImpl(Map<String,GpioPin> gpioPinMap,
                            PiBaseService piBaseService){
        this.digitalInput = (GpioPinDigitalInput) gpioPinMap.get("gasSenser");
        this.piBaseService = piBaseService;
    }


    /**
     * TTL输出有效信号为低电平（当输出低电平时信号灯亮）
     * @return
     */
    @Override
    public void getEdge() {
        digitalInput.setDebounce(200);
        digitalInput.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                log.info("[气体传感器] 感受到异常，已经发送短信，注意安全！ GasPinState={}",event.getState());
                piBaseService.turnOnBuzzer();
                piBaseService.turnOnFirst();
                SmsSender.sendSms(to);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                piBaseService.turnOffFirst();
                piBaseService.turnOffBuzzer();
            }
        });
    }
}
