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
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PiGasServiceImpl(GpioController controller,
                            PiBaseService piBaseService){
        this.digitalInput = controller.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
        this.piBaseService = piBaseService;
    }


    /**
     * TTL输出有效信号为低电平（当输出低电平时信号灯亮）
     * @return
     */
    @Override
    public void getEdge() {
        digitalInput.setShutdownOptions(true);
        digitalInput.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (PinState.LOW.equals(event.getState())){
                    piBaseService.turnOnBuzzer();
                    SmsSender.sendSms(to);
                    log.info("[气体传感器] 感受到异常，已经发送短信，注意安全！ GasPinState={}",event.getState());
                }else {
                    piBaseService.turnOffBuzzer();
                    log.info("[气体传感器] 状态变化 GasPinState={}",event.getState());
                }
            }
        });
    }
}
