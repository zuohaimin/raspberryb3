package cn.edu.swpu.raspberryb3.sensor;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class DHT11 {
    private GpioPinDigitalMultipurpose in;

    @Autowired
    public DHT11(Map<String,GpioPin> gpioPinMap) {
        in = (GpioPinDigitalMultipurpose) gpioPinMap.get("dhSenser");
    }

    public void getDHMessage() throws InterruptedException {
        Thread.sleep(1000);
        while (true) {
            in.low();
            Thread.sleep(20);
            in.high();
            in.setMode(PinMode.DIGITAL_INPUT);
            while (in.isHigh()) {
                continue;
            }
            while (in.isLow()) {
                continue;
            }
            int j = 0;
            int data[] = new int[40];
            while (j < 40) {
                int k = 0;
                while (in.isLow()) {
                    continue;
                }
                while (in.isHigh()) {
                    k += 1;
                    if (k > 100) {
                        break;
                    }
                }
                if (k < 8) {
                    data[j] = 0;
                } else {
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
        }
    }
}
