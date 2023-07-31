package com.lcc.controller.api;

import com.lcc.entity.DoctorInfo;
import com.lcc.entity.User;
import com.lcc.entity.UserPrePayInfo;
import com.lcc.service.DocService;
import com.lcc.service.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ClassName: DocOnlyController
 * Package: com.lcc.controller.api
 * Description:
 *
 * @Author lcc
 * @Create 2023/5/1 14:41
 * @Version
 */
@Controller
@RequestMapping("/api/doc")
public class DocOnlyController {

    @Resource
    DocService docService;

    @Resource
    ShareService shareService;

    @RequestMapping("/updatePassword")
    public String updatePassword(HttpSession session, Model model, @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (!docService.comparePassword(session, oldPassword)) {
            String message = "旧密码输入错误！";
            model.addAttribute("message", message);
            return "redirect:/page/doc/infoManage";
        }
        try {
            docService.updatePassword(session, newPassword);
            String message = "密码修改成功,请重新登录";
            model.addAttribute("message", message);
        } catch (Exception e) {
            String message = "密码修改失败";
            model.addAttribute("message", message);
        }
        return "redirect:/page/doc/infoManage";
    }

    @RequestMapping(value = "/updateDocInfo", method = RequestMethod.POST)
    public String updateDocInfo(@RequestParam("realName") String realName,
                                @RequestParam("age") int age,
                                @RequestParam("gender") String gender,
                                @RequestParam("saveAge") int saveAge,
                                @RequestParam("phone") String phone,
                                Model model, HttpSession session) {
        User user = shareService.findUser(session);
        try {
            docService.updateDocInfo(user.getUid(), realName, age, gender, saveAge, phone);
            model.addAttribute("message", "信息修改成功");
        } catch (Exception e) {
            model.addAttribute("message", "修改失败，请稍后再试");
        }
        return "redirect:/page/doc/infoManage";
    }

    @RequestMapping("/agree")
    public String agreePreUser(Model model, HttpSession session, @RequestParam("pid") int pid) {
        User user = shareService.findUser(session);
        try {
            docService.approvePreUser(user.getUid(), pid);
            model.addAttribute("message", "同意/负责-成功！");
        } catch (Exception e) {
            model.addAttribute("message", "操作失败！请稍后再试！");
        }
        return "redirect:/page/doc/approvePre";
    }

    @RequestMapping("/likeSearchNoDid")
    public String likeSearchNoDid(Model model, HttpSession session, @RequestParam("likeSearch") String likeSearch) {
        if (likeSearch == null || likeSearch.isEmpty()) {
            return "redirect:/page/doc/approvePre";
        } else {
            model.addAttribute("likeSearch", likeSearch);
            model.addAttribute("user", shareService.findUser(session));
            if ("%".equals(likeSearch)) likeSearch += "@";
            model.addAttribute("preInfoList", docService.likeSearchNoDid(model, likeSearch));
        }
        return "/doc/approvePreInfo";
    }

    @RequestMapping("/likeSearchInDid")
    public String likeSearchInDid(Model model, HttpSession session, @RequestParam("likeSearch") String likeSearch) {
        if (likeSearch == null || likeSearch.isEmpty()) {
            return "redirect:/page/doc/jiLuAndCM";
        } else {
            model.addAttribute("likeSearch", likeSearch);
            User user = shareService.findUser(session);
            model.addAttribute("user", user);
            if ("%".equals(likeSearch)) likeSearch += "@";
            model.addAttribute("preInfoList", docService.likeSearchInDid(model, user.getUid(), likeSearch));
        }
        return "/doc/tijianJLAndCM";
    }

    @RequestMapping("/breakPre")
    public String breakPre(Model model, @RequestParam("uid") int uid, @RequestParam("pid") int pid) {
        try {
            docService.userBreakPre(uid, pid);
            model.addAttribute("message", "操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "违约失败！请稍后再试！");
        }
        return "redirect:/page/doc/jiLuAndCM";
    }

    @RequestMapping("/secondStore")
    public String secondStore(Model model, @RequestParam("uid") int uid, @RequestParam("pid") int pid,
                              @RequestParam("jiLU") String jiLU, @RequestParam("comment") String comment) {
        model.addAttribute("uid", uid);
        model.addAttribute("pid", pid);
        try {
            docService.updateJiluAndComment(jiLU, comment, pid);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "暂存失败，请稍后再试！");
        }
        return "redirect:/page/doc/tijianComment";
    }

    @RequestMapping("/endingTiJian")
    public String endingTiJian(Model model, @RequestParam("uid") int uid, @RequestParam("pid") int pid) {
        model.addAttribute("uid", uid);
        model.addAttribute("pid", pid);
        try {
            docService.updateEndTime(pid);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "结束失败，请稍后再试！");
        }
        return "redirect:/page/doc/tijianComment";
    }

    @RequestMapping("/submitAndStore")
    public String submitAndStore(Model model, @RequestParam("pid") int pid,
                                 @RequestParam("jiLU") String jiLU, @RequestParam("comment") String comment) {
        model.addAttribute("pid", pid);
        try {
            docService.updateTiJianState(pid);
            docService.updateJiluAndComment(jiLU, comment, pid);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "提交失败，请稍后再试！");
        }
        return "redirect:/page/doc/jiLuAndCM";
    }
}
