package cn.edu.swpu.raspberryb3.entitys;

import com.sun.xml.internal.ws.spi.db.DatabindingException;
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

    private int id;
    private Double tempetature;
    private Double humidity;
    private DatabindingException time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTempetature() {
        return tempetature;
    }

    public void setTempetature(Double tempetature) {
        this.tempetature = tempetature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public DatabindingException getTime() {
        return time;
    }

    public void setTime(DatabindingException time) {
        this.time = time;
    }
}
