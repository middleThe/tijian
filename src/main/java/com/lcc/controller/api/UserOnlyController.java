package com.lcc.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.entity.PreGoodsInfo;
import com.lcc.entity.User;
import com.lcc.mapper.PreGoodsMapper;
import com.lcc.mapper.UserPrePayInfoMapper;
import com.lcc.service.ShareService;
import com.lcc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 * ClassName: UserOnlyController
 * Package: com.lcc.controller.api
 * Description: user模块功能实现
 *
 * @Author lcc
 * @Create 2023/4/15 11:04
 * @Version
 */
@Controller
@RequestMapping("/api/user")
public class UserOnlyController {

    @Resource
    UserService userService;

    @Resource
    ShareService shareService;

    @RequestMapping("/updatePassword")
    public String updatePassword(HttpSession session, Model model, @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (!userService.comparePassword(session, oldPassword)) {
            String message = "旧密码输入错误！";
            model.addAttribute("message", message);
            return "redirect:/page/user/infoManage";
        }
        try {
            userService.updatePassword(session, newPassword);
            String message = "密码修改成功,请重新登录";
            model.addAttribute("message", message);
        } catch (Exception e) {
            String message = "密码修改失败";
            model.addAttribute("message", message);
        }
        return "redirect:/page/user/infoManage";
    }

    @RequestMapping("/updateInfo")
    public String updateInfo(HttpSession session, Model model,
                             @RequestParam("realName") String realName,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone,
                             @RequestParam("birthday") String birthday,
                             @RequestParam("gender") String gender) {

        try {
            userService.updateUserInfo(session, realName, email, Date.valueOf(birthday), gender, phone);
            String message = "修改成功！";
            model.addAttribute("message", message);
        } catch (Exception e) {
            String message = "修改错误！请稍候再试！";
            model.addAttribute("message", message);
        }
        return "redirect:/page/user/infoManage";
    }

    @Resource
    UserPrePayInfoMapper userPrePayInfoMapper;

    @RequestMapping("/comment")
    public String comment(Model model, @RequestParam("prenumber") String prenumber, @RequestParam("comment") String comment) {
        try {
            userService.comment(prenumber, comment);
            model.addAttribute("message", "评价成功");
            return "redirect:/page/user/history";
        } catch (Exception e) {
            model.addAttribute("message", "评价失败，请稍后再试！");
            model.addAttribute("pid", userPrePayInfoMapper.getPidByPreNumber(prenumber));
        }
        return "redirect:/page/user/commentHistory";
    }

    @RequestMapping("/productULikesearch")
    public String productULikesearch(Model model, HttpSession session, @RequestParam("likeSearch") String likeSearch,
                                     @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", userService.getTop5Product());
        if (likeSearch == null || likeSearch.isEmpty()) {
            return "redirect:/page/user/productUser";
        } else {
            model.addAttribute("likeSearch", likeSearch);
            if (likeSearch.equals("%")) likeSearch += "@";
            PageHelper.startPage(pageNum, 6);
            List<PreGoodsInfo> productList = userService.getProductByLikeSearch(likeSearch);
            PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
            model.addAttribute("productList", productList);
            model.addAttribute("pageInfo", pageInfo);
            String type = "likeSearch";
            model.addAttribute("pageType", type);
        }
        return "/user/product";
    }

    @Resource
    PreGoodsMapper preGoodsMapper;

    @RequestMapping("/getGoodsPrSmall200")
    public String getGoodsPreSmall(Model model, HttpSession session,
                                   @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", userService.getTop5Product());
        PageHelper.startPage(pageNum, 6);
        List<PreGoodsInfo> productList = preGoodsMapper.getGoodsPriceSmall200();
        PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        String type = "small200";
        model.addAttribute("pageType", type);
        return "/user/product";
    }

    @RequestMapping("/getGoodsPrBig200")
    public String getGoodsPreBig(Model model, HttpSession session,
                                 @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", userService.getTop5Product());
        PageHelper.startPage(pageNum, 6);
        List<PreGoodsInfo> productList = preGoodsMapper.getGoodsPriceBig200();
        PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        String type = "big200";
        model.addAttribute("pageType", type);
        return "/user/product";
    }

    @RequestMapping("/getGoodsByClasses")
    public String getGoodsByClasses(Model model, HttpSession session, @RequestParam("classes") String classes,
                                    @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
//        System.out.println(classes);
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", userService.getTop5Product());
        PageHelper.startPage(pageNum, 6);
        List<PreGoodsInfo> productList = userService.getGoodsByClasses(classes);
        PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        String type = "classes";
        model.addAttribute("pageType", type);
        model.addAttribute("classes", classes);
        return "/user/product";
    }

    @RequestMapping("/getGoodsByPeople")
    public String getGoodsByPeople(Model model, HttpSession session, @RequestParam("forpeople") String forpeople,
                                   @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", userService.getTop5Product());
        PageHelper.startPage(pageNum, 6);
        List<PreGoodsInfo> productList = userService.getGoodsByForPeople(forpeople);
        PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        String type = "forpeople";
        model.addAttribute("pageType", type);
        model.addAttribute("forpeople", forpeople);
        return "/user/product";
    }

    @RequestMapping("/preGoods")
    public String preGoods(Model model, HttpSession session, @RequestParam("gid") int gid, @RequestParam("pretime") String pretime) {
        try {
            userService.addPreUser(model, session, gid, Date.valueOf(pretime));
            model.addAttribute("message", "预约成功");
        } catch (Exception e) {
            String message = (String) model.getAttribute("message");
            if (message == null) model.addAttribute("message", "预约失败！请稍后再试！");
            model.addAttribute("gid", gid);
            return "redirect:/page/user/productSingle";
        }
        return "redirect:/page/user/productUser";
    }

    @RequestMapping(value = "/removePreGoods", method = RequestMethod.POST)
    public String removePreGoods(Model model, @RequestParam("pid") int pid) {
        try {
            userService.removePrePayInfo(pid, model);
            model.addAttribute("message", "取消成功");
        } catch (Exception e) {
            String message = (String) model.getAttribute("message");
            if (message == null) {
                model.addAttribute("message", "取消失败，请稍后再试");
            }
        }
        return "redirect:/page/user/preProductInfo";
    }

    @RequestMapping("/preInfoLikeSearch")
    public String preInfoLikeSearch(Model model, HttpSession session, @RequestParam("likeSearch") String likeSearch) {
        if (likeSearch == null || likeSearch.isEmpty()) {
            return "redirect:/page/user/preProductInfo";
        } else {
            model.addAttribute("likeSearch", likeSearch);
            if (likeSearch.equals("%")) likeSearch += "@";
            User user = shareService.findUser(session);
            model.addAttribute("user", user);
            model.addAttribute("preInfoList", userService.likeSearchPreInfo(user.getUid(), likeSearch, model));
        }
        return "/user/preProductInfo";
    }
}