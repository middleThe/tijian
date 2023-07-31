package com.lcc.service;

import com.lcc.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * ClassName: UserService
 * Package: com.lcc.service.impl
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/23 11:14
 * @Version
 */
public interface ShareService {
    void register(String username,
                  String password,
                  String realName,
                  String gender,
                  String phone,
                  String email,
                  Date birthday,
                  Model model);


    User findUser(HttpSession session);

}
