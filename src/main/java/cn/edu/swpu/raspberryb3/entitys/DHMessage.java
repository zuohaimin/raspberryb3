package cn.edu.swpu.raspberryb3.entitys;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:05
 * @Description:
 */
@Getter
@Setter
@ToString
public class DHMessage {
    private String tempetature;

    private String humidity;
}
