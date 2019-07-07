package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.service.PiBaseService;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: 束手就擒
 * @Date: 19-7-3 下午9:58
 * @Description:
 */
public class PiBaseServiceImpl implements PiBaseService {

    @Autowired
    private GpioController gpioController;

    @Autowired
    private GpioPinDigitalOutput led;

    @Autowired
    private GpioPinDigitalOutput buzzer;

    @Override
    public int turnOnLight() {
        GpioPinDigitalOutput output = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01,"led", PinState.LOW);

        return 0;
    }

    @Override
    public int turnOffLight() {
        return 0;
    }

    @Override
    public int turnOnBuzzer() {
        return 0;
    }

    @Override
    public int turnOffBuzzer() {
        return 0;
    }
}
