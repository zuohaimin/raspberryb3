package cn.edu.swpu.raspberryb3.service;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;

import java.util.List;
import java.util.Map;

public interface DHMessageService {

    List<Map<String, DHMessage>> getDHMessage() throws InterruptedException;

    boolean collectDHInformation();
}
