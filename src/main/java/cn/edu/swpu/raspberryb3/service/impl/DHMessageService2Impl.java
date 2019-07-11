package cn.edu.swpu.raspberryb3.service.impl;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.pi4j.io.gpio.PinMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
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

    private String getHTMessage() throws InterruptedException {
        int j = 0;
        int [] data = new int[40];

        dhSenser.low();
        Thread.sleep(20);
        dhSenser.high();
        dhSenser.setMode(PinMode.DIGITAL_INPUT);

        while (dhSenser.isHigh()){
            continue;
        }

        while (dhSenser.isLow()){
            continue;
        }

        while (j<40){
            int k = 0;
            while (dhSenser.isLow()){
                 continue;
            }
            while (dhSenser.isHigh()){
                k += 1;
                if (k > 100){
                    break;
                }
            }
            if (k < 8){
                data[j] = 0;
            }else {
                data[j] = 1;
            }

            j++;
        }
        System.out.println("sensor is working");
        System.out.println(data.toString());

        int humidity_bit[] = Arrays.copyOfRange(data, 0, 8);
        int humidity_point_bit[] = Arrays.copyOfRange(data, 8, 16);
        int temperature_bit[] = Arrays.copyOfRange(data, 16, 24);
        int temperature_point_bit[] = Arrays.copyOfRange(data, 24, 32);
        int check_bit[] = Arrays.copyOfRange(data, 32, 40);

        int humidity = 0;
        int humidity_point = 0;
        int temperature = 0;
        int temperature_point = 0;
        int check = 0;

        for (int i = 0; i < 8; i++) {
            humidity += humidity_bit[i] * Math.pow(2, (7 - i));
            humidity_point += humidity_point_bit[i] * Math.pow(2, (7 - i));
            temperature += temperature_bit[i] * Math.pow(2, (7 - i));
            temperature_point += temperature_point_bit[i] * Math.pow(2, (7 - i));
            check += check_bit[i] * Math.pow(2, (7 - i));
        }
        int temp = humidity + humidity_point + temperature + temperature_point;
        if (check == temp) {
            System.out.println("temperature:" + temperature + " humidity:" + humidity);
        } else {
            System.out.println("Wrong");
            System.out.println("temperature:" + temperature + " humidity:" + humidity + " check:" + check);
        }
        Thread.sleep(5000);
        return null;
    }





}
