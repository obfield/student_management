package com.koko.controller;

import com.koko.dto.ResponseBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @PostMapping({"/","/login"})
    public ResponseBean login(String username, String password){
        return new ResponseBean();
    }
}
