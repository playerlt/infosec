package com.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author index
 * @date 2020/6/15
 **/
@Controller
public class MyFirstController {
    /*
    / 代表从当前项目下开始，处理当前项目下的hello请求
     */
    @RequestMapping("/hello")
    public String myfirstRequest(){
        System.out.println("请求收到了....正在处理中");

        return "success";
    }
}
