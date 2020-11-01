package com.codeman.mall4sb.controller.home;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhanghongjie
 * @description:
 * @date: 2020/11/1 10:59
 * @version: 1.0
 */
@CrossOrigin
@RestController
public class HomeController {

    @GetMapping("/init")
    public String initData() {
        return "hello vue, this is spring boot";
    }
}
