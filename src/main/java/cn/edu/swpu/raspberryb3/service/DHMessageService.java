package cn.edu.swpu.raspberryb3.service;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;

public interface DHMessageService {

    DHMessage getDHMessage() throws InterruptedException;

    boolean collectDHInformation();
}
