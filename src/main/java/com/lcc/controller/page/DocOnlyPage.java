package com.lcc.controller.page;

import com.lcc.entity.User;
import com.lcc.service.DocService;
import com.lcc.service.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * ClassName: DocOnlyPage
 * Package: com.lcc.controller.page
 * Description: 医生页面模块
 *
 * @Author lcc
 * @Create 2023/3/25 19:04
 * @Version
 */
@Controller
@RequestMapping("/page/doc")
public class DocOnlyPage {
    @Resource
    ShareService shareService;

    @Resource
    DocService docService;

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        User user = shareService.findUser(session);
        model.addAttribute("user", user);
        model.addAttribute("alreadyInfoList", docService.getIndexInfo(user.getUid(), model));
        return "/doc/index";
    }

    @RequestMapping("/infoManage")
    public String infoManage(HttpSession session, Model model,
                             @RequestParam(required = false, value = "message") String message) {
        User user = shareService.findUser(session);
        model.addAttribute("user", user);
        model.addAttribute("docInfo", docService.getDocInfo(user.getUid()));
        if (message != null) model.addAttribute("message", message);
        return "/doc/infoManage";
    }

    @RequestMapping("/toNotice")
    public String toNotice(HttpSession session, Model model) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("notice", docService.getNotice(model));
        return "/doc/notice";
    }

    @RequestMapping("/approvePre")
    public String approvePre(HttpSession session, Model model,
                             @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("preInfoList", docService.getPayInfo(model));
        if (message != null) model.addAttribute("message", message);
        return "/doc/approvePreInfo";
    }

    @RequestMapping("/jiLuAndCM")
    public String jiLuAndCM(HttpSession session, Model model,
                            @RequestParam(required = false, value = "message") String message) {
        User user = shareService.findUser(session);
        model.addAttribute("user", user);
        model.addAttribute("preInfoList", docService.getPreInfoByDid(model, user.getUid()));
        if (message != null) model.addAttribute("message", message);
        return "/doc/tijianJLAndCM";
    }

    @RequestMapping("/tijianComment")
    public String tijianComment(HttpSession session, Model model, @RequestParam("uid") int uid, @RequestParam("pid") int pid,
                                @RequestParam(required = false, value = "message") String message) {
        model.addAttribute("user", shareService.findUser(session));
        model.addAttribute("preInfo", docService.getPrePayInfoAndSetUserInfo(model, uid, pid));
        if (message != null) model.addAttribute("message", message);
        return "/doc/tijian";
    }
}
