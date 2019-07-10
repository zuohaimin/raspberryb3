package cn.edu.swpu.raspberryb3.config.pi4j;

import com.pi4j.io.gpio.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: 束手就擒
 * @Date: 19-7-3 下午9:59
 * @Description:
 */

@Configuration
public class Pi4jConfig {

    /**
     * pin编号采用WiringPin, 详情参照 img/20141211142821890.jpeg
     * @return
     */
    @Bean
    public GpioController gpioController(){
        return GpioFactory.getInstance();
    }

    /**
     * 初始化引脚
     * @param gpioController
     * @return
     */
    @Bean
    public Map<String,GpioPin> gpioPinMap(GpioController gpioController){
        Map<String,GpioPin> gpioPinMap = new HashMap<>();

        gpioPinMap.put("led_1",gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW));
        gpioPinMap.put("gasSenser",gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP));
        gpioPinMap.put("voiceSenser",gpioController.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_UP));
        gpioPinMap.put("dhSenser",gpioController.provisionDigitalInputPin(RaspiPin.GPIO_03,PinPullResistance.PULL_DOWN));
        gpioPinMap.put("led_2",gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_04,PinState.LOW));
        gpioPinMap.put("buzzer",gpioController.provisionPwmOutputPin(RaspiPin.GPIO_23));
        return gpioPinMap;
    }
}
