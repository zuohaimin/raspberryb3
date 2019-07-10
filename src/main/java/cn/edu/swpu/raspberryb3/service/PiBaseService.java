package cn.edu.swpu.raspberryb3.service;

/**
 * @Author: 束手就擒
 * @Date: 19-7-3 下午9:32
 * @Description:
 */
public interface PiBaseService {

    boolean turnOnFirst();

    boolean turnOnSecond();

    boolean turnOffSecond();

    boolean turnOffFirst();


    boolean turnOnBuzzer();

    boolean turnOffBuzzer();

}
