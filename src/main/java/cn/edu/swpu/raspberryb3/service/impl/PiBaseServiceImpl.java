package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.constant.RaspberryConstant;
import cn.edu.swpu.raspberryb3.service.PiBaseService;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: 束手就擒
 * @Date: 19-7-3 下午9:58
 * @Description:
 *
 * buzzer: 无源蜂鸣器需要pwm,支持PWM的引脚23/24/26/01
 */
public class PiBaseServiceImpl implements PiBaseService {

    private GpioPinDigitalOutput led;

    private GpioPinPwmOutput buzzer;
    
    @Autowired
    public PiBaseServiceImpl(GpioController gpioController) {
        led = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, "led", PinState.LOW);
        buzzer = gpioController.provisionPwmOutputPin(RaspiPin.GPIO_23, "buzzer");
    }


    /**
     * 开灯
     * @return
     */
    @Override
    public boolean turnOnLight() {
        led.high();
        return led.isHigh();
    }

    /**
     * 关灯
     * @return
     */
    @Override
    public boolean turnOffLight() {
        led.low();
        return led.isLow();
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
