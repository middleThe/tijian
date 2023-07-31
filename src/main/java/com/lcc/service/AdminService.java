package com.lcc.service;

import com.lcc.entity.DoctorInfo;
import com.lcc.entity.PreGoodsInfo;
import com.lcc.entity.UserAlreadyInfo;
import com.lcc.entity.UserPrePayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

/**
 * ClassName: DocService
 * Package: com.lcc.service
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/27 18:15
 * @Version
 */
public interface AdminService {
    List<DoctorInfo> getDocInfo();

    void addDoctor(String username,
                   String password,
                   String realName,
                   int age,
                   String gender,
                   int saveAge,
                   String phone,
                   String title,
                   Model model);

    List<DoctorInfo> getDocInfoByLike(String realName);

    void delDoc(int did);

    void updateDocInfo(int did, String realName, int age, String gender, int saveAge, String phone, String title);

    List<UserPrePayInfo> getUserPrePayInfoNotOk(Model model);

    void toAgree(int pid);

    List<UserPrePayInfo> likeSearchByPreNumber(String preNumber, Model model);

    List<UserAlreadyInfo> getUserAlreadyTiJianInfo(Model model);

    List<UserAlreadyInfo> alreadyInfoLikeSearch(String preNumber, Model model);

    void issueNotice(String title, String content, String role);

    List<PreGoodsInfo> getAllProduct();

    List<PreGoodsInfo> getProductByLikeSearch(String likeSearch);

    List<PreGoodsInfo> getTop5Product();

    void delProductByGid(int gid, String goodsName);

    PreGoodsInfo getInfoByGid(int gid);

    void updateGoodsInfo(double price, String classes, String goodsinfo, String attentioninfo, String goodsname, String forage);

    void addGoods(String goodsName, String pic, double price, String goodsInfo, String attentionInfo, String forage, String forPeople, String classes, String projectInfo);

    int upAndDownShelf(int gid);

    List<PreGoodsInfo> getAllGoodsByIfShelf(int ifShelf);
}
