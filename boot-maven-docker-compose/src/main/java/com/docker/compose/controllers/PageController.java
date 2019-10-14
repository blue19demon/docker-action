package com.docker.compose.controllers;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/pages")
@Controller
public class PageController {
	/**
     * 本地访问内容地址 ：http://localhost:8080/hello
     * @param map
     * @return
     */
    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map, Model model) {
        model.addAttribute("say","欢迎欢迎,热烈欢迎");
        map.put("hello", "欢迎进入HTML页面");
        return "index";
    }
    /**
     * 本地访问内容地址 ：http://localhost:8080/hello
     * @param map
     * @return
     */
    @RequestMapping("/login")
    public String login(HashMap<String, Object> map, Model model) {
        return "login";
    }
    
    @RequestMapping("/dep/list")
    public String depLists(HashMap<String, Object> map, Model model) {
    	model.addAttribute("say","欢迎欢迎,热烈欢迎");
        map.put("hello", "欢迎进入HTML页面");
        return "/deps/list";
    }
}
