package com.lcc.service;

import com.lcc.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * ClassName: UserService
 * Package: com.lcc.service.impl
 * Description:
 *
 * @Author lcc
 * @Create 2023/4/15 19:14
 * @Version
 */
public interface UserService {

    void indexInfo(int uid, Model model);

    boolean comparePassword(HttpSession session, String password);

    void updatePassword(HttpSession session, String password);

    UserInfo getUserInfo(int uid);

    void updateUserInfo(HttpSession session, String realName, String email, Date birthday, String gender, String phone);

    List<UserPrePayInfo> getUserUserPrePayInfo(HttpSession session, Model model);

    UserPrePayInfo getInfoByPid(int pid, Model model);

    void comment(String prenumber, String comment);

    Notice getNotice(Model model);

    List<PreGoodsInfo> getAllProduct();

    List<PreGoodsInfo> getTop5Product();

    List<PreGoodsInfo> getProductByLikeSearch(String likeSearch);

    List<PreGoodsInfo> getGoodsByClasses(String classes);

    List<PreGoodsInfo> getGoodsByForPeople(String forpeople);

    PreGoodsInfo getSingleGoods(int gid);

    void addPreUser(Model model, HttpSession session, int gid, Date pretime);

    List<UserPrePayInfo> getPrePayInfo(int uid, Model model);

    void removePrePayInfo(int pid, Model model);

    List<UserPrePayInfo> likeSearchPreInfo(int uid, String likeSearch, Model model);


}
