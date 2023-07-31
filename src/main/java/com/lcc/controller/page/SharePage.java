package com.lcc.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: Page
 * Package: com.lcc.controller
 * Description: 全局页面模块
 *
 * @Author lcc
 * @Create 2023/3/20 11:20
 * @Version
 */
@Controller
@RequestMapping("/page/share")
public class SharePage {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    //登录错误
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        String error = "账号或密码错误";
        model.addAttribute("loginError", error);
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
