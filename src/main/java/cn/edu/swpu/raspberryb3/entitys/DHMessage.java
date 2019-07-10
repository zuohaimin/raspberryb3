package cn.edu.swpu.raspberryb3.entitys;

import cn.edu.swpu.raspberryb3.utils.KeyUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: 束手就擒
 * @Date: 19-7-8 下午5:05
 * @Description:
 */
@Getter
@Setter
@ToString
public class DHMessage implements Serializable{
    private String tempetature;
    private String humidity;
    private String time;

    public DHMessage(){
        this.time = KeyUtil.genDHMessageTime();
    }
}
