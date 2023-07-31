package com.lcc.controller.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.entity.DoctorInfo;
import com.lcc.entity.PreGoodsInfo;
import com.lcc.mapper.DocMapper;
import com.lcc.mapper.PreGoodsMapper;
import com.lcc.mapper.UserMapper;
import com.lcc.mapper.UserPrePayInfoMapper;
import com.lcc.service.AdminService;
import com.lcc.service.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ClassName: AdminOnlyPage
 * Package: com.lcc.controller.page
 * Description: 管理员页面模块
 *
 * @Author lcc
 * @Create 2023/3/25 19:04
 * @Version
 */
@Controller
@RequestMapping("/page/admin")
public class AdminOnlyPage {

    @Resource
    ShareService shareService;

    @Resource
    AdminService adminService;

    @Resource
    UserMapper userMapper;
    @Resource
    DocMapper docMapper;
    @Resource
    PreGoodsMapper preGoodsMapper;
    @Resource
    UserPrePayInfoMapper userPrePayInfoMapper;


    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("userTotal", userMapper.getUserInfoTotal());
        model.addAttribute("docTotal", docMapper.getDoctorTotal());
        model.addAttribute("goodsTotal", preGoodsMapper.getGoodsTotal());
        model.addAttribute("prePayTotal", userPrePayInfoMapper.getPrePayTotal());
        return "/admin/index";
    }

    @RequestMapping("/docInfo")
    public String docInfo(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("DocInfoList", adminService.getDocInfo());
        return "/admin/docInfo";
    }

    @RequestMapping("/docRegister")
    public String docRegister() {
        return "/admin/docRegister";
    }

    @RequestMapping(value = "/docInfoModify", method = RequestMethod.POST)
    @ResponseBody
    public DoctorInfo docInfoModify(HttpServletRequest request, @RequestBody DoctorInfo doctorInfo) {
        HttpSession session = request.getSession();
        session.setAttribute("doctorInfo", doctorInfo);
        String username = userMapper.getUsernameByUid(doctorInfo.getUid());
        session.setAttribute("username", username);
        return doctorInfo;
    }

    @RequestMapping("/toDocInfoModify")
    public String toDocInfoModify(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("doctorInfo", session.getAttribute("doctorInfo"));
        model.addAttribute("username", session.getAttribute("username"));
        return "/admin/docInfoModify";
    }

    @RequestMapping("/toUserPretiJianInfo")
    public String toUserPretiJianInfo(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("userPrePayInfoList", adminService.getUserPrePayInfoNotOk(model));
        return "/admin/userPretiJianInfo";
    }

    @RequestMapping("/toUserOldInfo")
    public String toUserOldInfo(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("alreadyTiJianList", adminService.getUserAlreadyTiJianInfo(model));
        return "/admin/userAlreadyTijianInfo";
    }

    @RequestMapping(value = "/notice")
    public String notice(HttpSession session, Model model,
                         @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        if (message != null) model.addAttribute("message", message);
        return "/admin/annunciate";
    }

    @RequestMapping("/product")
    public String product(HttpSession session, Model model,
                          @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum,
                          @RequestParam(required = false, value = "message") String message) {

        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("TOP5List", adminService.getTop5Product());
        PageHelper.startPage(pageNum, 6);
        List<PreGoodsInfo> productList = adminService.getAllProduct();
        PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        if (message != null) model.addAttribute("message", message);
        return "/admin/product";

    }

    @RequestMapping(value = "/toProductModify")
    public String toProductModify(HttpSession session, Model model, @RequestParam("gid") int gid) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("preGoodsInfo", adminService.getInfoByGid(gid));
        return "/admin/productModify";
    }

    @RequestMapping("/toProductAdd")
    public String toProductAdd(HttpSession session, Model model, @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        if (message != null) model.addAttribute("message", message);
        return "/admin/productAdd";
    }
}
