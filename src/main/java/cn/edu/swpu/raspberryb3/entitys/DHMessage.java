package cn.edu.swpu.raspberryb3.entitys;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:05
 * @Description:
 */
@Getter
@Setter
@ToString
public class DHMessage implements Serializable{

    private int id;
    private Double tempetature;
    private Double humidity;
    private Date time;

}
