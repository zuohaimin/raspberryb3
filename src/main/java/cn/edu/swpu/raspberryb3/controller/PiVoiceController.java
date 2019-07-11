package cn.edu.swpu.raspberryb3.controller;

import cn.edu.swpu.raspberryb3.service.PiBaseService;
import cn.edu.swpu.raspberryb3.service.PiVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/raspberry3")
public class PiVoiceController {

    @Autowired
    private PiVoiceService piVoiceService;

    @Autowired
    private PiBaseService piBaseService;

    @RequestMapping(value = "/voicesensor", method = RequestMethod.GET)
    private Map<String, String> getEdge() {
        piVoiceService.getEdge();
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("status", "success");
        return statusMap;
    }

    @RequestMapping(value = "/turnOnFirst", method = RequestMethod.GET)
    private void turnOnFirst() {
      piBaseService.turnOnFirst();
    }

    @RequestMapping(value = "/turnOnSecond", method = RequestMethod.GET)
    private void turnOnSecond() {
        piBaseService.turnOnSecond();
    }

    @RequestMapping(value = "/turnOffSecond", method = RequestMethod.GET)
    private void turnOffSecond() {

        piBaseService.turnOffSecond();
    }

    @RequestMapping(value = "/turnOnBuzzer", method = RequestMethod.GET)
    private void turnOnBuzzer() {
        piBaseService.turnOnBuzzer();
    }

    @RequestMapping(value = "/turnOffFirst", method = RequestMethod.GET)
    private void turnOffFirst() {
        piBaseService.turnOffFirst();
    }

    @RequestMapping(value = "/turnOffBuzzer", method = RequestMethod.GET)
    private void turnOffBuzzer() {
        piBaseService.turnOffBuzzer();
    }





}
