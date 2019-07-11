package cn.edu.swpu.raspberryb3.service.impl;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Author: 束手就擒
 * @Date: 19-7-11 下午3:34
 * @Description:
 */
public class DHMessageService2Impl {

    private GpioPinDigitalMultipurpose dhSenser;

    @Autowired
    public DHMessageService2Impl(Map<String,GpioPin> gpioPinMap){
        this.dhSenser = (GpioPinDigitalMultipurpose) gpioPinMap.get("dhSenser");
    }



}
