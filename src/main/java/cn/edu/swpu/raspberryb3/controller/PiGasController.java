package cn.edu.swpu.raspberryb3.controller;

import cn.edu.swpu.raspberryb3.service.PiGasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/raspberry3")
public class PiGasController {

    @Autowired
    private PiGasService piGasService;

    @RequestMapping(value = "/gassensor", method = RequestMethod.GET)
    private Map<String, String> getEdge() {
        piGasService.getEdge();
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("status", "success");
        return statusMap;
    }

}
