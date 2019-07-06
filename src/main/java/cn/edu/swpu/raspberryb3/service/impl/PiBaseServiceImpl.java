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

    private GpioPinDigitalOutput led;

    private GpioPinDigitalOutput buzzer;
    
    @Autowired
    public PiBaseServiceImpl(GpioController gpioController){
        led = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01,"led", PinState.LOW);
        buzzer = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_03,"buzzer", PinState.LOW);
    }



    @Override
    public boolean turnOnLight() {
        led.high();
        return led.isHigh();
    }

    @Override
    public boolean turnOffLight() {
        led.low();
        return led.isLow();
    }

    @Override
    public boolean turnOnBuzzer() {
        return true;
    }

    @Override
    public boolean turnOffBuzzer() {
        return true;
    }
}
