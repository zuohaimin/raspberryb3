package cn.edu.swpu.raspberryb3.config.pi4j;

import com.pi4j.io.gpio.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
