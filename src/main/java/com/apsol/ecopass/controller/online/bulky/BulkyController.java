package com.apsol.ecopass.controller.online.bulky;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/api/bulky")
public class BulkyController {

    @GetMapping("/index")
    public String bulkyIndex(){

        return "online/bulky/request";
    }
}
