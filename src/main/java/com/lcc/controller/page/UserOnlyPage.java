package com.lcc.controller.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.entity.PreGoodsInfo;
import com.lcc.entity.User;
import com.lcc.entity.UserInfo;
import com.lcc.service.ShareService;
import com.lcc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ClassName: UserOnlyPage
 * Package: com.lcc.controller.page
 * Description: user页面显示
 *
 * @Author lcc
 * @Create 2023/3/25 19:02
 * @Version
 */

@Controller
@RequestMapping("/page/user")
public class UserOnlyPage {

    @Resource
    ShareService shareService;

    @Resource
    UserService userService;

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        User user = shareService.findUser(session);
        model.addAttribute("user", user);
        userService.indexInfo(user.getUid(), model);
        return "/user/index";
    }


    @RequestMapping("/infoManage")
    public String infoManage(HttpSession session, Model model,
                             @RequestParam(required = false, value = "message") String message) {
        User user = shareService.findUser(session);
        model.addAttribute("user", user);
        UserInfo userInfo = userService.getUserInfo(user.getUid());
        model.addAttribute("userInfo", userInfo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("date", simpleDateFormat.format(userInfo.getBirthday()));
        if (message != null) model.addAttribute("message", message);
        return "/user/infoManage";
    }

    @RequestMapping("/history")
    public String history(HttpSession session, Model model,
                          @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("prePayInfoList", userService.getUserUserPrePayInfo(session, model));
        if (message != null) model.addAttribute("message", message);
        return "/user/tijianHistory";
    }

    @RequestMapping("/moreHistory")
    public String moreHistory(HttpSession session, Model model, @RequestParam("pid") int pid) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("userPrePayInfo", userService.getInfoByPid(pid, model));
        return "/user/tijianHistoryMoreInfo";
    }

    @RequestMapping("/commentHistory")
    public String commentHistory(HttpSession session, Model model, @RequestParam("pid") int pid,
                                 @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("userPrePayInfo", userService.getInfoByPid(pid, model));
        if (message != null) model.addAttribute("message", message);
        return "/user/tijianHistoryComment";
    }

    @RequestMapping("/toNotice")
    public String toNotice(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("notice", userService.getNotice(model));
        return "/user/notice";
    }

    @RequestMapping("/productUser")
    public String productUser(HttpSession session, Model model,
                              @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum,
                              @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", userService.getTop5Product());
        PageHelper.startPage(pageNum, 6);
        List<PreGoodsInfo> productList = userService.getAllProduct();
        PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        if (message != null) model.addAttribute("message", message);
        return "/user/product";
    }

    @RequestMapping("/productSingle")
    public String productSingle(HttpSession session, Model model, @RequestParam("gid") int gid,
                                @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("info", userService.getSingleGoods(gid));
        if (message != null) model.addAttribute("message", message);
        return "/user/productSingle";
    }

    @RequestMapping("/preProductInfo")
    public String preProductInfo(HttpSession session, Model model,
                                 @RequestParam(required = false, value = "message") String message) {
        User user = shareService.findUser(session);
        model.addAttribute("user", user);
        model.addAttribute("preInfoList", userService.getPrePayInfo(user.getUid(), model));
        if (message != null) model.addAttribute("message", message);
        return "/user/preProductInfo";
    }
}
