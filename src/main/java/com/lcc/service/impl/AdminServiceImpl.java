package com.lcc.service.impl;

import com.lcc.entity.*;
import com.lcc.mapper.*;
import com.lcc.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: DocServiceImpl
 * Package: com.lcc.service.impl
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/27 18:16
 * @Version
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    DocMapper docMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserPrePayInfoMapper userPrePayInfoMapper;

    @Resource
    PreGoodsMapper preGoodsMapper;

    @Resource
    UserAlreadyInfoMapper userAlreadyInfoMapper;

    @Resource
    NoticeMapper noticeMapper;

    @Override
    public List<DoctorInfo> getDocInfo() {
        return docMapper.getDoctorInfo();
    }

    @Override
    @Transactional
    public void addDoctor(String username, String password, String realName, int age, String gender, int saveAge, String phone, String title, Model model) {
        if (userMapper.getUserInfoByRealName(username) == null) {
            if (docMapper.getDoctorInfoByRealName(realName) == null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                User user = new User(0, username, "doc", encoder.encode(password));
                if (!userMapper.setUser(user)) {
                    String message = "请重新提交";
                    model.addAttribute("message", message);
                    throw new RuntimeException("医生添加失败");
                }
                if (!docMapper.addDoctor(user.getUid(), realName, age, gender, saveAge, phone, title)) {
                    String message = "请重新提交";
                    model.addAttribute("message", message);
                    throw new RuntimeException("医生信息添加失败");
                }

            } else {
                String message = "该医生已有绑定的账号";
                model.addAttribute("message", message);
                throw new RuntimeException("医生信息添加失败");
            }

        } else {
            String message = "用户名存在，注册失败";
            model.addAttribute("message", message);
            throw new RuntimeException("医生添加失败");
        }
    }

    @Override
    public List<DoctorInfo> getDocInfoByLike(String likeSearch) {//likeSearch
        if (StringUtils.isNumeric(likeSearch)) {
            likeSearch = "%" + likeSearch + "%";
            return docMapper.getDoctorInfoByDidLike(likeSearch);
        } else {
            likeSearch = "%" + likeSearch + "%";
            return docMapper.getDoctorInfoByRealNameLike(likeSearch);
        }
    }

    @Override
    @Transactional
    public void delDoc(int did) {
        int uid = docMapper.getDocUidByDid(did);
        if (!docMapper.delDocInfo(did)) throw new RuntimeException("删除错误");
        else userMapper.delUser(uid);
    }

    @Override
    public void updateDocInfo(int did, String realName, int age, String gender, int saveAge, String phone, String title) {
        if (!StringUtils.isNumeric(phone)) throw new RuntimeException("更新错误");
        if (!docMapper.updateDocInfo(realName, age, gender, saveAge, phone, title, did))
            throw new RuntimeException("更新错误");
    }

    @Override
    public List<UserPrePayInfo> getUserPrePayInfoNotOk(Model model) {
        List<UserPrePayInfo> userPrePayInfoList = userPrePayInfoMapper.getPrePayInfoByNotOk();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<String> dateList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<String> phoneList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            dateList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
            nameList.add(userMapper.getRealNameByUid(userPrePayInfo.getUid()));
            phoneList.add(userMapper.getPhoneByUid(userPrePayInfo.getUid()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
        }
        model.addAttribute("preDateList", dateList);
        model.addAttribute("nameList", nameList);
        model.addAttribute("phoneList", phoneList);
        model.addAttribute("goodsNameList", goodsNameList);
        return userPrePayInfoList;
    }

    @Override
    public void toAgree(int pid) {
        if (!userPrePayInfoMapper.toAgree(pid)) throw new RuntimeException("操作失败");
    }

    @Override
    public List<UserPrePayInfo> likeSearchByPreNumber(String preNumber, Model model) {
        preNumber = "%" + preNumber + "%";
        List<UserPrePayInfo> userPrePayInfoList = userPrePayInfoMapper.likeSearchByPreNumber(preNumber);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<String> dateList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<String> phoneList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            dateList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
            nameList.add(userMapper.getRealNameByUid(userPrePayInfo.getUid()));
            phoneList.add(userMapper.getPhoneByUid(userPrePayInfo.getUid()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
        }
        model.addAttribute("preDateList", dateList);
        model.addAttribute("nameList", nameList);
        model.addAttribute("phoneList", phoneList);
        model.addAttribute("goodsNameList", goodsNameList);
        return userPrePayInfoList;
    }

    @Override
    public List<UserAlreadyInfo> getUserAlreadyTiJianInfo(Model model) {
        List<UserAlreadyInfo> userAlreadyInfoList = userAlreadyInfoMapper.getUserAlreadyTiJianInfo();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<String> userRealNameList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        List<String> docNameList = new ArrayList<>();
        List<String> endTimeList = new ArrayList<>();
        for (UserAlreadyInfo userAlreadyInfo : userAlreadyInfoList) {
            userRealNameList.add(userMapper.getRealNameByUid(userAlreadyInfo.getUid()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userAlreadyInfo.getGid()));
            docNameList.add(docMapper.getRealNameByDid(userAlreadyInfo.getDid()));
            endTimeList.add(simpleDateFormat.format(userAlreadyInfo.getTime()));
        }
        model.addAttribute("userRealNameList", userRealNameList);
        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("docNameList", docNameList);
        model.addAttribute("endTimeList", endTimeList);

        return userAlreadyInfoList;
    }

    @Override
    public List<UserAlreadyInfo> alreadyInfoLikeSearch(String likeSearch, Model model) {

        likeSearch = "%" + likeSearch + "%";
        List<UserAlreadyInfo> userAlreadyInfoList = userAlreadyInfoMapper.getAlreadyInfoLikeSearchByPrenumber(likeSearch);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<String> userRealNameList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        List<String> docNameList = new ArrayList<>();
        List<String> endTimeList = new ArrayList<>();
        assert userAlreadyInfoList != null;
        for (UserAlreadyInfo userAlreadyInfo : userAlreadyInfoList) {
            userRealNameList.add(userMapper.getRealNameByUid(userAlreadyInfo.getUid()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userAlreadyInfo.getGid()));
            docNameList.add(docMapper.getRealNameByDid(userAlreadyInfo.getDid()));
            endTimeList.add(simpleDateFormat.format(userAlreadyInfo.getTime()));
        }
        model.addAttribute("userRealNameList", userRealNameList);
        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("docNameList", docNameList);
        model.addAttribute("endTimeList", endTimeList);

        return userAlreadyInfoList;
    }

    @Override
    public void issueNotice(String title, String content, String role) {
        if (!noticeMapper.insertNotice(title, content, new Date(), role)) throw new RuntimeException("插入失败");
    }

    @Override
    public List<PreGoodsInfo> getAllProduct() {
        return preGoodsMapper.getAllProducts();
    }

    @Override
    public List<PreGoodsInfo> getProductByLikeSearch(String likeSearch) {
        likeSearch = "%" + likeSearch + "%";
        return preGoodsMapper.getProductsByLikeSearch(likeSearch);
    }

    @Override
    public List<PreGoodsInfo> getTop5Product() {
        return preGoodsMapper.getTop5Product();
    }

    @Override
    public void delProductByGid(int gid, String goodsName) {
        if (!preGoodsMapper.deleteProductByGid(gid)) {
            throw new RuntimeException("删除失败！");
        } else {
            File file = new File("F:\\Code-java\\tijian\\src\\main\\webapp\\WEB-INF\\static\\picture\\" + goodsName + ".jpg");
            if (file.exists() && file.isFile()) System.out.println(file.delete());
        }
    }

    @Override
    public PreGoodsInfo getInfoByGid(int gid) {
        return preGoodsMapper.getInfoByGid(gid);
    }

    @Override
    public void updateGoodsInfo(double price, String classes, String goodsinfo, String attentioninfo, String goodsname, String forage) {
        if (!preGoodsMapper.updatePreGoodsInfoByGoodsName(price, classes, goodsinfo, attentioninfo, goodsname, forage))
            throw new RuntimeException("更新失败!");
    }

    @Override
    public void addGoods(String goodsName, String pic, double price, String goodsInfo, String attentionInfo, String forage, String forPeople, String classes, String projectInfo) {
        if (pic.endsWith(".jpg")) pic = "../../static/picture/" + goodsName + ".jpg";
        else if (pic.endsWith(".png")) pic = "../../static/picture/" + goodsName + ".png";
        if (!preGoodsMapper.addGoods(goodsName, pic, price, goodsInfo, attentionInfo, forage, forPeople, classes, projectInfo))
            throw new RuntimeException("添加失败！");
    }

    @Override
    public int upAndDownShelf(int gid) {
        int ifShelf = preGoodsMapper.getIfShelfByGid(gid);
        if (!preGoodsMapper.upOrDownTheShelf(ifShelf ^ 1, gid)) {
            throw new RuntimeException("上架失败！");
        }
        return ifShelf;
    }

    @Override
    public List<PreGoodsInfo> getAllGoodsByIfShelf(int ifShelf) {
        return preGoodsMapper.getAllGoodsByIfShelf(ifShelf);
    }


}
