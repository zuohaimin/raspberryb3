package cn.edu.swpu.raspberryb3.sensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DHT11 {
    private static final int    MAXTIMINGS  = 85;
    private final int[]         dht11_dat   = { 0, 0, 0, 0, 0 };

    @Autowired
    private GpioController gpioController;

    public DHT11() {

        // setup wiringPi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }

        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
    }

    public void getTemperature(final int pin) {
        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

        Gpio.pinMode(pin, Gpio.OUTPUT);
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.pinMode(pin, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pin) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pin);

            if (counter == 255) {
                break;
            }

            /* ignore first 3 transitions */
            if (i >= 4 && i % 2 == 0) {
                /* shove each bit into the storage bytes */
                dht11_dat[j / 8] <<= 1;
                if (counter > 16) {
                    dht11_dat[j / 8] |= 1;
                }
                j++;
            }
        }
        // check we read 40 bits (8bit x 5 ) + verify checksum in the last
        // byte
        if (j >= 40 && checkParity()) {
            float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (h > 100) {
                h = dht11_dat[0]; // for DHT11
            }
            float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (c > 125) {
                c = dht11_dat[2]; // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0) {
                c = -c;
            }
            final float f = c * 1.8f + 32;
            System.out.println("Humidity = " + h + " Temperature = " + c + "(" + f + "f)");
        } else {
            System.out.println("Data not good, skip");
        }

    }

    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

//    public static void main(final String ars[]) throws Exception {
//
//        final DHT11 dht = new DHT11();
//
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(2000);
//            dht.getTemperature(21);
//        }
//
//        System.out.println("Done!!");
//
//    }

    public void getDHMessage() throws InterruptedException {
        while (true) {
            GpioPinDigitalOutput pin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_07);
            pin.low();
            Thread.sleep(2);
            pin.high();
            GpioPinDigitalInput in = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_07);
            while (in.isLow()) {
                continue;
            }
            while (in.isHigh()) {
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
