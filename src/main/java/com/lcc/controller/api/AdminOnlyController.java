package com.lcc.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcc.entity.PreGoodsInfo;
import com.lcc.mapper.PreGoodsMapper;
import com.lcc.service.AdminService;
import com.lcc.service.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


/**
 * ClassName: AdminOnlyController
 * Package: com.lcc.controller.api
 * Description: 管理员模块功能实现
 *
 * @Author lcc
 * @Create 2023/3/27 18:17
 * @Version
 */
@Controller
@RequestMapping("/api/admin")
public class AdminOnlyController {

  @Resource
  AdminService adminService;

  @Resource
  ShareService shareService;

  @RequestMapping(value = "/doDocRegister", method = RequestMethod.POST)
  public String addDoc(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("realname") String realName,
                       @RequestParam("age") int age,
                       @RequestParam("gender") String gender,
                       @RequestParam("saveage") int saveAge,
                       @RequestParam("phone") String phone,
                       @RequestParam("title") String title, Model model, HttpSession session) {

    try {
      adminService.addDoctor(username, password, realName, age, gender, saveAge, phone, title, model);
      model.addAttribute("user", shareService.findUser(session));
      model.addAttribute("DocInfoList", adminService.getDocInfo());

      return "/admin/docInfo";
    } catch (Exception e) {
      e.printStackTrace();
      return "/admin/docRegister";
    }
  }


  @RequestMapping(value = "/getDocInfo", method = RequestMethod.GET)
  public String getDocInfoByLike(@RequestParam("likeSearch") String likeSearch, Model model, HttpSession session) {
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("likeSearch", likeSearch);
    if ("%".equals(likeSearch))return "/admin/docInfo";
    if (likeSearch == null || likeSearch.isEmpty()) {
      model.addAttribute("DocInfoList", adminService.getDocInfo());
    } else {
      model.addAttribute("DocInfoList", adminService.getDocInfoByLike(likeSearch));
    }
    return "/admin/docInfo";
  }

  @RequestMapping(value = "/delDoc", method = RequestMethod.GET)
  public String delDoc(@RequestParam("did") int did, Model model, HttpSession session) {
    model.addAttribute("user", shareService.findUser(session));
    adminService.delDoc(did);
    model.addAttribute("DocInfoList", adminService.getDocInfo());
    return "/admin/docInfo";
  }


  @RequestMapping(value = "/modifyDocInfo", method = RequestMethod.POST)
  public String modifyDocInfo(@RequestParam("did") int did,
                              @RequestParam("realname") String realName,
                              @RequestParam("age") int age,
                              @RequestParam("gender") String gender,
                              @RequestParam("saveage") int saveAge,
                              @RequestParam("phone") String phone,
                              @RequestParam("title") String title, Model model, HttpSession session) {
    try {
      adminService.updateDocInfo(did, realName, age, gender, saveAge, phone, title);
    } catch (Exception e) {
      String message = "修改失败";
      model.addAttribute("message", message);
    }
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("DocInfoList", adminService.getDocInfo());
    return "/admin/docInfo";
  }


  @RequestMapping(value = "/agree", method = RequestMethod.POST)
  public String agree(Model model, @RequestParam("pid") int pid, HttpSession session) {
    try {
      adminService.toAgree(pid);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("userPrePayInfoList", adminService.getUserPrePayInfoNotOk(model));
    return "/admin/userPretiJianInfo";
  }

  @RequestMapping("/likeSearchPreInfo")
  public String likeSearchPreInfo(Model model, @RequestParam("preNumber") String preNumber, HttpSession session) {
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("preNumber", preNumber);
    if ("%".equals(preNumber))return "/admin/userPretiJianInfo";
    if (preNumber == null || preNumber.isEmpty()) {
      model.addAttribute("userPrePayInfoList", adminService.getUserPrePayInfoNotOk(model));
    } else {
      model.addAttribute("userPrePayInfoList", adminService.likeSearchByPreNumber(preNumber, model));
    }
    return "/admin/userPretiJianInfo";
  }

  @RequestMapping("/likeSearchAlreadyInfo")
  public String likeSearchAlreadyInfo(Model model, @RequestParam("likeSearch") String likeSearch, HttpSession session) {
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("likeSearch", likeSearch);
    if ("%".equals(likeSearch))return "/admin/userAlreadyTijianInfo";
    if (likeSearch == null || likeSearch.isEmpty()) {
      model.addAttribute("alreadyTiJianList", adminService.getUserAlreadyTiJianInfo(model));
    } else {
      model.addAttribute("alreadyTiJianList", adminService.alreadyInfoLikeSearch(likeSearch, model));
    }
    return "/admin/userAlreadyTijianInfo";
  }

  @RequestMapping(value = "/issueNotice", method = RequestMethod.POST)
  public String issueNotice(Model model, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("role") String role, HttpSession session) {
//    System.out.println(title + content + role);
    if (title.isEmpty() || content.isEmpty()) {
      model.addAttribute("message", "标题或内容不可为空！");
      return "redirect:/page/admin/notice";
    }
    model.addAttribute("user", shareService.findUser(session));
    try {
      adminService.issueNotice(title, content, role);
      model.addAttribute("message", "发布成功！");
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("message", e.getMessage());
    }
    return "/admin/annunciate";
  }

  @RequestMapping("/likeSearchByGoodsName")
  public String likeSearchByGoodsName(Model model, HttpSession session, @RequestParam("likeSearch") String likeSearch,
                                      @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("TOP5List", adminService.getTop5Product());
    if (likeSearch == null || likeSearch.isEmpty()) {
      return "redirect:/page/admin/product";
    } else {
      model.addAttribute("likeSearch", likeSearch);
      if (likeSearch.equals("%")) likeSearch += "@";
      PageHelper.startPage(pageNum, 6);
      List<PreGoodsInfo> productList = adminService.getProductByLikeSearch(likeSearch);
      PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);
      model.addAttribute("productList", productList);
      model.addAttribute("pageInfo", pageInfo);
      String type = "likeSearch";
      model.addAttribute("pageType", type);
    }
    return "/admin/product";
  }

  @Resource
  PreGoodsMapper preGoodsMapper;

  @RequestMapping("/delGoods")
  public String delGoods(Model model, @RequestParam("gid") int gid) {
    try {
      adminService.delProductByGid(gid, preGoodsMapper.getGoodsNameByGid(gid));
      model.addAttribute("message", "删除成功！");
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("message", "删除失败！");
    }
    return "redirect:/page/admin/product";
  }

  @RequestMapping(value = "/modifyProduct", method = RequestMethod.POST)
  public String modifyProduct(Model model,
                              @RequestParam("goodsname") String goodsname,
                              @RequestParam("price") double price,
                              @RequestParam("classes") String classes,
                              @RequestParam("goodsinfo") String goodsinfo,
                              @RequestParam("attentioninfo") String attentioninfo,
                              @RequestParam("forage") String forage) {
    try {
      adminService.updateGoodsInfo(price, classes, goodsinfo, attentioninfo, goodsname, forage);
      model.addAttribute("message", "更新成功!");
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("message", e.getMessage());
    }
    return "redirect:/page/admin/product";
  }

  @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
  public String addProduct(Model model,
                           @RequestParam("goodsname") String goodsname,
                           @RequestParam("forage") String forage,
                           @RequestParam("forpeople") String forpeople,
                           @RequestParam("price") double price,
                           @RequestParam("classes") String classes,
                           @RequestParam("projectinfo") String projectinfo,
                           @RequestParam("goodsinfo") String goodsinfo,
                           @RequestParam("attentioninfo") String attentioninfo,
                           @RequestParam("file") MultipartFile file) throws IOException {

    File pic = new File("F:\\Code-java\\tijian\\src\\main\\webapp\\WEB-INF\\static\\picture\\" + goodsname + ".jpg");
    if (file == null || file.isEmpty()) {
      model.addAttribute("message", "图片不能为空！");
      return "redirect:/page/admin/toProductAdd";
    }
    if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".jpg") && !file.getOriginalFilename().endsWith(".png")) {
      model.addAttribute("message", "图片仅支持'.jpg'和'.png格式'");
      return "redirect:/page/admin/toProductAdd";
    }

    System.out.println(pic.createNewFile());
    file.transferTo(pic);

    try {
      adminService.addGoods(goodsname, pic.getAbsolutePath(), price, goodsinfo, attentioninfo, forage, forpeople, classes, projectinfo);
      model.addAttribute("message", "添加成功");
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("message", "添加失败");
    }
    return "redirect:/page/admin/product";
  }


  @RequestMapping("/upOrDownGoods")
  public String upOrDownGoods(Model model,@RequestParam("gid") int gid) {
    try {
      int ifShelf = adminService.upAndDownShelf(gid);
      if (ifShelf == 0) model.addAttribute("message", "上架成功！");
      else model.addAttribute("message", "下架成功！");
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("message", e.getMessage());
    }
    return "redirect:/page/admin/product";
  }


  @RequestMapping("/productByIfShelf")
  public String productByIfShelf(Model model, HttpSession session, @RequestParam("ifshelf") int ifShelf,
                                 @RequestParam(defaultValue = "1", required = false, value = "pageNum") int pageNum) {
    model.addAttribute("user", shareService.findUser(session));
    model.addAttribute("TOP5List",adminService.getTop5Product());

    PageHelper.startPage(pageNum, 6);
    List<PreGoodsInfo> productList = adminService.getAllGoodsByIfShelf(ifShelf);
    PageInfo<PreGoodsInfo> pageInfo = new PageInfo<>(productList);

    model.addAttribute("productList",productList);
    model.addAttribute("pageInfo", pageInfo);

    String type = "ifShelfPage";
    model.addAttribute("pageType",type);
    model.addAttribute("ifShelf",ifShelf);

    return "/admin/product";

  }
}
