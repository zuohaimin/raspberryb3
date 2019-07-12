package cn.edu.swpu.raspberryb3.dao;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DHMessageMapper {

    @Select("SELECT * FROM th.th ORDER BY id DESC LIMIT 1")
    DHMessage queryTopOne();

}
