package com.lcc.service.impl;

import com.lcc.entity.User;
import com.lcc.mapper.UserMapper;
import com.lcc.service.ShareService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * ClassName: UserServiceImpl
 * Package: com.lcc.service.impl
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/23 11:20
 * @Version
 */

@Service
public class ShareServiceImpl implements ShareService {

    @Resource
    UserMapper userMapper;

    @Transactional
    @Override
    public void register(String username, String password, String realName, String gender, String phone, String email, Date birthday, Model model) {

        if (userMapper.getInfoByUsername(username) == null) {

            if (userMapper.getUserInfoByRealName(realName) != null) {
                String message = "该用户已有绑定的账号";
                model.addAttribute("message", message);
                throw new RuntimeException("用户信息添加失败");
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(0, username, "user", encoder.encode(password));

            if (!userMapper.setUser(user)) {
                String message = "请重新提交";
                model.addAttribute("message", message);
                throw new RuntimeException("用户添加失败");
            }

            if (!userMapper.setUserInfo(user.getUid(), realName, email, birthday, gender, phone)) {
                String message = "请重新提交";
                model.addAttribute("message", message);
                throw new RuntimeException("用户信息添加失败");
            } else {
                String message = "注册成功,请登录";
                model.addAttribute("loginError", message);
            }

        } else {
            String message = "用户名存在，注册失败";
            model.addAttribute("message", message);
            throw new RuntimeException("用户添加失败");
        }
    }

    @Override
    public User findUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = userMapper.getInfoByUsername(authentication.getName());
            session.setAttribute("user", user);
        }
        return user;
    }


}
