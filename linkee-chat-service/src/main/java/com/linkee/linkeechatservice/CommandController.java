package com.linkee.linkeechatservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {


    @GetMapping("/info")
    public void info(){
        System.out.println("연결 확인!!!!!!!!!");
    }

}
