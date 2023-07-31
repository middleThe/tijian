package com.lcc.controller.api;

import com.lcc.service.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.sql.Date;

/**
 * ClassName: UserApiController
 * Package: com.lcc.controller.api
 * Description: 全局共享功能实现
 *
 * @Author lcc
 * @Create 2023/3/22 20:04
 * @Version
 */
@Controller
@RequestMapping("/api/share")
public class ShareApiController {

    @Resource
    ShareService shareService;

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String doRegister(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("realname") String realName,
                             @RequestParam("gender") String gender,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email,
                             @RequestParam("birthday") String birthday, Model model) {

//        System.out.println(username+" "+password+" "+realName+" "+gender+" "+phone+" "+email+" "+birthday);
        try {
            shareService.register(username, password, realName, gender, phone, email, Date.valueOf(birthday), model);
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            return "register";
        }
    }

}
