package cn.edu.swpu.raspberryb3.service;

/**
 * @Author: 束手就擒
 * @Date: 19-7-7 下午10:44
 * @Description:
 */
public interface PiSensor {

    /**
     * 感受到刺激，完成相应动作
     */
    void getEdge();
}
