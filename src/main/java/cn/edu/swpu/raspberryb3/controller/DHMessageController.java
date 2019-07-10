package cn.edu.swpu.raspberryb3.controller;

import cn.edu.swpu.raspberryb3.entitys.DHMessage;
import cn.edu.swpu.raspberryb3.service.DHMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/raspberry3")
public class DHMessageController {

    @Autowired
    private DHMessageService dhMessageService;

    @RequestMapping(value = "/dhmessage", method = RequestMethod.GET)
    private DHMessage getTemperature() throws InterruptedException {
        return dhMessageService.getDHMessage();
    }

    @RequestMapping(value = "/collectdhmessage", method = RequestMethod.GET)
    private Map<String, String> collectDHMessage() {
        Map<String, String> statusMap = new HashMap<>();
        if (dhMessageService.collectDHInformation()) {
            statusMap.put("status", "success");
        } else {
            statusMap.put("status", "fail");
        }
        return statusMap;
    }
}
