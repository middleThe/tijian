package com.lcc.service.impl;

import com.lcc.entity.*;
import com.lcc.mapper.*;
import com.lcc.service.ShareService;
import com.lcc.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: UserServiceImpl
 * Package: com.lcc.service.impl
 * Description:
 *
 * @Author lcc
 * @Create 2023/4/15 20:15
 * @Version
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    ShareService shareService;

    @Resource
    UserMapper userMapper;

    @Resource
    UserPrePayInfoMapper userPrePayInfoMapper;

    @Resource
    DocMapper docMapper;

    @Resource
    PreGoodsMapper preGoodsMapper;

    @Resource
    UserAlreadyInfoMapper userAlreadyInfoMapper;

    @Resource
    NoticeMapper noticeMapper;

    @Override
    public void indexInfo(int uid, Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        UserInfo userInfo = userMapper.getUserInfoByUid(uid);
        List<Integer> gidList = userPrePayInfoMapper.getGidByUid(uid);
        List<String> goodsNameList = new ArrayList<>();
        List<Double> priceList = new ArrayList<>();
        for (Integer gid : gidList) {
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(gid));
            priceList.add(preGoodsMapper.getPriceByGid(gid));
        }
        model.addAttribute("birthday", simpleDateFormat.format(userInfo.getBirthday()));
        model.addAttribute("userinfo", userInfo);
        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("priceList", priceList);
    }

    @Override
    public boolean comparePassword(HttpSession session, String password) {
        User user = shareService.findUser(session);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public void updatePassword(HttpSession session, String password) {
        User user = shareService.findUser(session);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!userMapper.updatePassword(encoder.encode(password), user.getUsername()))
            throw new RuntimeException("密码修改失败");
    }

    @Override
    public UserInfo getUserInfo(int uid) {
        return userMapper.getUserInfoByUid(uid);
    }

    @Override
    public void updateUserInfo(HttpSession session, String realName, String email, Date birthday, String gender, String phone) {
        User user = shareService.findUser(session);
        if (!userMapper.updateUserInfo(realName, email, birthday, gender, phone, user.getUid())) {
            throw new RuntimeException("修改错误！");
        }
    }

    @Override
    public List<UserPrePayInfo> getUserUserPrePayInfo(HttpSession session, Model model) {
        User user = shareService.findUser(session);
        List<UserPrePayInfo> userPrePayInfoList = userPrePayInfoMapper.getUserPrePayInfo(user.getUid());
        List<String> doctorNameList = new ArrayList<>();
        List<String> preTimeList = new ArrayList<>();
        List<String> startTimeList = new ArrayList<>();
        List<String> endTimeList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        List<Integer> commentList = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd ");

        for (UserPrePayInfo info : userPrePayInfoList) {
            doctorNameList.add(docMapper.getRealNameByDid(info.getDid()));
            preTimeList.add(simpleDateFormat1.format(info.getPretime()));
            startTimeList.add(simpleDateFormat.format(info.getStarttime()));
            endTimeList.add(simpleDateFormat.format(info.getEndtime()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(info.getGid()));
            commentList.add(userAlreadyInfoMapper.getCommentByPreNumber(info.getPrenumber()));
        }

        model.addAttribute("doctorNameList", doctorNameList);
        model.addAttribute("preTimeList", preTimeList);
        model.addAttribute("startTimeList", startTimeList);
        model.addAttribute("endTimeList", endTimeList);
        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("commentList", commentList);

        return userPrePayInfoList;
    }


    @Override
    public UserPrePayInfo getInfoByPid(int pid, Model model) {
        UserPrePayInfo userPrePayInfo = userPrePayInfoMapper.getInfoByPid(pid);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd ");

        String doctorName = docMapper.getRealNameByDid(userPrePayInfo.getDid());
        String preTime = simpleDateFormat1.format(userPrePayInfo.getPretime());
        String startTime = simpleDateFormat.format(userPrePayInfo.getStarttime());
        String endTime = simpleDateFormat.format(userPrePayInfo.getEndtime());
        String goodsName = preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid());
        int comment = userAlreadyInfoMapper.getCommentByPreNumber(userPrePayInfo.getPrenumber());
        String realName = userMapper.getRealNameByUid(userPrePayInfo.getUid());

        model.addAttribute("doctorName", doctorName);
        model.addAttribute("preTime", preTime);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("goodsName", goodsName);
        model.addAttribute("comment", comment);
        model.addAttribute("realName", realName);

        return userPrePayInfo;
    }

    @Override
    public void comment(String prenumber, String comment) {
        if (!userAlreadyInfoMapper.comment(prenumber, comment)) throw new RuntimeException("评价失败！");
    }

    @Override
    public Notice getNotice(Model model) {
        Notice notice = noticeMapper.getNoticeForUser();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("time", simpleDateFormat.format(notice.getCreate_time()));
        return notice;
    }

    @Override
    public List<PreGoodsInfo> getAllProduct() {
        return preGoodsMapper.getProductShelf();
    }

    @Override
    public List<PreGoodsInfo> getTop5Product() {
        return preGoodsMapper.getTop5Product();
    }

    @Override
    public List<PreGoodsInfo> getProductByLikeSearch(String likeSearch) {
        likeSearch = "%" + likeSearch + "%";
        return preGoodsMapper.getProductsByLikeSearch(likeSearch);
    }

    @Override
    public List<PreGoodsInfo> getGoodsByClasses(String classes) {
        return preGoodsMapper.getGoodsByClasses(classes);
    }

    @Override
    public List<PreGoodsInfo> getGoodsByForPeople(String forpeople) {
        return preGoodsMapper.getGoodsByForPeople(forpeople);
    }

    @Override
    public PreGoodsInfo getSingleGoods(int gid) {
        return preGoodsMapper.getInfoByGid(gid);
    }

    @Override
    @Transactional
    public void addPreUser(Model model, HttpSession session, int gid, Date pretime) {
        User user = shareService.findUser(session);
        int noCome = userMapper.getNoComeByUid(user.getUid());
        if (noCome > 3) {
            model.addAttribute("message", "您已违约超过三次，预约失败！");
            throw new RuntimeException();
        }
        Random random = new Random();
        int prenumber;
        while (true) {
            prenumber = random.nextInt(90000000) + 10000000;
            Integer pid = userPrePayInfoMapper.getPidByPreNumber(prenumber + "");
            if (pid == null) break;
        }
        if (!userPrePayInfoMapper.addPreUser(user.getUid(), gid, prenumber + "", pretime)) {
            throw new RuntimeException("预约失败");
        }
        if (!preGoodsMapper.addGoodsCount(preGoodsMapper.getCountByGid(gid) + 1, gid)) {
            throw new RuntimeException("预约失败");
        }
    }

    @Override
    public List<UserPrePayInfo> getPrePayInfo(int uid, Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<UserPrePayInfo> userPrePayInfoList = userPrePayInfoMapper.getPrePayInfo(uid);
        List<String> goodsNameList = new ArrayList<>();
        List<String> preTimeList = new ArrayList<>();
        List<String> docNameList = new ArrayList<>();
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
            preTimeList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
            if (userPrePayInfo.getDid() == null) {
                docNameList.add(null);
            } else {
                docNameList.add(docMapper.getRealNameByDid(userPrePayInfo.getDid()));
            }
        }

        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("preTimeList", preTimeList);
        model.addAttribute("docNameList", docNameList);

        return userPrePayInfoList;
    }

    @Override
    public void removePrePayInfo(int pid, Model model) {
        int ifok = userPrePayInfoMapper.getIfOkByPid(pid);
        if (ifok == 0) {
            if (!userPrePayInfoMapper.removePrePayInfo(pid)) throw new RuntimeException("删除失败!");
        } else {
            Date preTime = userPrePayInfoMapper.getPreTimeByPid(pid);
            Date now = new Date();
            long diffTime = TimeUnit.DAYS.convert(preTime.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
            if (diffTime <= 2) {
                model.addAttribute("message", "已错过预约取消时间，预约开始的两天前,不可再取消预约");
                throw new RuntimeException();
            } else {
                if (!userPrePayInfoMapper.removePrePayInfo(pid)) throw new RuntimeException("删除失败!");
            }
        }
    }

    @Override
    public List<UserPrePayInfo> likeSearchPreInfo(int uid, String likeSearch, Model model) {
        List<UserPrePayInfo> userPrePayInfoList = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNumeric(likeSearch)) {
            likeSearch = "%" + likeSearch + "%";
            userPrePayInfoList = userPrePayInfoMapper.likeSearchByPreNumberWithUser(uid, likeSearch);
        } else {
            likeSearch = "%" + likeSearch + "%";
            userPrePayInfoList = userPrePayInfoMapper.likeSearchByGoodsNameWithUser(uid, likeSearch);
        }
        List<String> goodsNameList = new ArrayList<>();
        List<String> preTimeList = new ArrayList<>();
        List<String> docNameList = new ArrayList<>();
        assert userPrePayInfoList != null;
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
            preTimeList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
            if (userPrePayInfo.getDid() == null) {
                docNameList.add(null);
            } else {
                docNameList.add(docMapper.getRealNameByDid(userPrePayInfo.getDid()));
            }
        }
        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("preTimeList", preTimeList);
        model.addAttribute("docNameList", docNameList);

        return userPrePayInfoList;
    }

}
