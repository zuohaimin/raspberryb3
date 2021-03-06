package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.constant.RaspberryConstant;
import cn.edu.swpu.raspberryb3.service.PiBaseService;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: 束手就擒
 * @Date: 19-7-3 下午9:58
 * @Description:
 *
 * buzzer: 无源蜂鸣器需要pwm,支持PWM的引脚23/24/26/01
 */
@Service
public class PiBaseServiceImpl implements PiBaseService {

    private GpioPinDigitalOutput ledFirst;

    private GpioPinDigitalOutput ledSecond;

    private GpioPinPwmOutput buzzer;

    @Autowired
    public PiBaseServiceImpl(Map<String,GpioPin> gpioPinMap) {
        this.ledFirst = (GpioPinDigitalOutput)gpioPinMap.get("led_1");
        this.ledSecond = (GpioPinDigitalOutput)gpioPinMap.get("led_2");
        this.buzzer = (GpioPinPwmOutput)gpioPinMap.get("buzzer");
    }


    /**
     * 开灯
     * @return
     */
    @Override
    public boolean turnOnFirst() {
        ledFirst.high();
        return ledSecond.isHigh();
    }

    @Override
    public boolean turnOnSecond() {

        ledSecond.high();
        return ledSecond.isHigh();
    }

    @Override
    public boolean turnOffSecond() {
        ledSecond.low();
        return ledSecond.isLow();
    }

    @Override
    public boolean turnOffFirst() {
        ledFirst.low();
        return ledFirst.isLow();
    }

    /**
     * 打开蜂鸣器，rate = 100
     * @return
     */
    @Override
    public boolean turnOnBuzzer() {
        buzzer.setPwm(RaspberryConstant.BUZZER_RATE);
        return buzzer.getPwm() == RaspberryConstant.BUZZER_RATE;
    }

    @Override
    public boolean turnOffBuzzer() {
        buzzer.setPwm(0);
        return buzzer.getPwm() == 0;
    }
}