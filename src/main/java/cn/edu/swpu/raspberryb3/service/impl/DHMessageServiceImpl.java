package cn.edu.swpu.raspberryb3.service.impl;

import cn.edu.swpu.raspberryb3.dal.RedisDal;
import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import cn.edu.swpu.raspberryb3.service.DHMessageService;
import cn.edu.swpu.raspberryb3.utils.KeyUtil;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
class DHMessageServiceImpl implements DHMessageService {

    @Autowired
    private GpioController gpioController;

    private final Pin DEFAULT_PIN = RaspiPin.GPIO_07;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    private RedisDal redisDal;

    /**
     * 获取采集的温度数据
     * @return
     */
    @Override
    public DHMessage getDHMessage() {
        return redisDal.getDHMessage(KeyUtil.getRedisKey());
    }

    @Override
    public boolean collectDHInformation() {
        if (!(atomicInteger.get() == 0)) {
            return false;
        }
        atomicInteger.getAndIncrement();
        Thread thread = new Thread(() -> {
            while (true) {
                GpioPinDigitalMultipurpose dht11 = gpioController.provisionDigitalMultipurposePin
                        (DEFAULT_PIN, PinMode.DIGITAL_INPUT);
                int data[] = new int[500];
                dht11.setMode(PinMode.DIGITAL_OUTPUT);

                try {
                    dht11.high();
                    Thread.sleep(1);
                    dht11.low();
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dht11.setMode(PinMode.DIGITAL_INPUT);
                dht11.setPullResistance(PinPullResistance.PULL_UP);
                for (int i = 0; i < 500; i++) {
                    data[i] = dht11.getState().getValue();
                }
                int bit_count = 0;
                int tmp = 0;
                int count = 0;
                String humidityBit = "";
                String temperatureBit = "";
                String crc = "";
                try {
                    while (data[count] == 1) {
                        tmp = 1;
                        count++;
                    }
                    for (int i = 0; i < 32; i++) {
                        bit_count = 0;
                        while (data[count] == 0) {
                            tmp = 1;
                            count++;
                        }
                        while (data[count] == 1) {
                            bit_count++;
                            count++;
                        }
                        if (bit_count > 3) {
                            if (i >= 0 && i < 8)
                                humidityBit = humidityBit + "1";
                            if (i >= 16 && i < 24) {
                                temperatureBit = temperatureBit + "1";
                            }
                        } else {
                            if (i >= 0 && i < 8)
                                humidityBit = humidityBit + "0";
                            if (i >= 16 && i < 24) {
                                temperatureBit = temperatureBit + "0";
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    for (int i = 0; i < 8; i++) {
                        bit_count = 0;
                        while (data[count] == 0) {
                            tmp = 1;
                            count++;
                        }
                        while (data[count] == 1) {
                            bit_count++;
                            count += 1;
                        }
                        if (bit_count > 3) {
                            crc = crc + "1";
                        } else {
                            crc = crc + "0";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                humidityBit = Integer.valueOf(humidityBit, 2).toString();
                temperatureBit = Integer.valueOf(temperatureBit, 2).toString();
                DHMessage dhMessage = new DHMessage();
                dhMessage.setHumidity(humidityBit);
                dhMessage.setTempetature(temperatureBit);
                redisDal.save(dhMessage);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return true;
    }
}
