package cn.edu.swpu.raspberryb3.config.pi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 束手就擒
 * @Date: 19-7-3 下午9:59
 * @Description:
 */

@Configuration
public class Pi4jConfig {

    @Bean
    public GpioController gpioController(){
        return GpioFactory.getInstance();
    }
}
