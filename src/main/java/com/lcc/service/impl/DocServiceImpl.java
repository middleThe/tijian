package com.lcc.service.impl;

import com.lcc.entity.*;
import com.lcc.mapper.*;
import com.lcc.service.DocService;
import com.lcc.service.ShareService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

/**
 * ClassName: DocServiceImpl
 * Package: com.lcc.service.impl
 * Description:
 *
 * @Author lcc
 * @Create 2023/5/1 14:42
 * @Version
 */
@Service
public class DocServiceImpl implements DocService {

    @Resource
    DocMapper docMapper;

    @Resource
    ShareService shareService;

    @Resource
    UserMapper userMapper;

    @Resource
    NoticeMapper noticeMapper;

    @Resource
    UserPrePayInfoMapper userPrePayInfoMapper;

    @Resource
    PreGoodsMapper preGoodsMapper;

    @Resource
    UserAlreadyInfoMapper userAlreadyInfoMapper;


    @Override
    public List<UserAlreadyInfo> getIndexInfo(int uid, Model model) {
        int did = docMapper.getDidByUid(uid);
        List<UserAlreadyInfo> userAlreadyInfoList = userAlreadyInfoMapper.getInfoByDid(did);
        List<String> goodsNameList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> endTimeList = new ArrayList<>();

        for (UserAlreadyInfo userAlreadyInfo : userAlreadyInfoList) {
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userAlreadyInfo.getGid()));
            endTimeList.add(simpleDateFormat.format(userAlreadyInfo.getTime()));
        }
        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("endTimeList", endTimeList);
        model.addAttribute("count", userAlreadyInfoMapper.getFinishCountByDid(did));
        model.addAttribute("badCount", userAlreadyInfoMapper.getBadCommentByDid(did));
        model.addAttribute("goodCount", userAlreadyInfoMapper.getGoodCommentByDid(did));
        model.addAttribute("goodsTotal", preGoodsMapper.getGoodsTotal());

        return userAlreadyInfoList;
    }

    @Override
    public DoctorInfo getDocInfo(int uid) {
        return docMapper.getDocInfoByUid(uid);
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
    public void updateDocInfo(int uid, String realName, int age, String gender, int saveAge, String phone) {
        if (!docMapper.updateDocInfoByUid(realName, age, gender, saveAge, phone, uid))
            throw new RuntimeException("医生信息更新失败");
    }

    @Override
    public Notice getNotice(Model model) {
        Notice notice = noticeMapper.getNoticeForDoc();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("time", simpleDateFormat.format(notice.getCreate_time()));
        return notice;
    }

    @Override
    public List<UserPrePayInfo> getPayInfo(Model model) {
        List<UserPrePayInfo> userPrePayInfoList = userPrePayInfoMapper.getPerPayInfoByNoDid();
        List<String> userNameList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> preTimeList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            userNameList.add(userMapper.getRealNameByUid(userPrePayInfo.getUid()));
            preTimeList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
        }
        model.addAttribute("userNameList", userNameList);
        model.addAttribute("preTimeList", preTimeList);
        model.addAttribute("goodsNameList", goodsNameList);

        return userPrePayInfoList;
    }

    @Override
    public void approvePreUser(int uid, int pid) {
        int did = docMapper.getDidByUid(uid);
        if (userPrePayInfoMapper.getIfOkByPid(pid) == 1) {
            if (!userPrePayInfoMapper.responsibleFor(did, pid)) throw new RuntimeException("负责失败！");
        } else {
            if (!userPrePayInfoMapper.approvePreUser(did, pid)) throw new RuntimeException("批准失败！");
        }
    }

    @Override
    public List<UserPrePayInfo> likeSearchNoDid(Model model, String likeSearch) {
        List<UserPrePayInfo> userPrePayInfoList;
        if (StringUtils.isNumeric(likeSearch)) {
            likeSearch = "%" + likeSearch + "%";
            userPrePayInfoList = userPrePayInfoMapper.likeSearchByPreNumberInNoDid(likeSearch);
        } else {
            likeSearch = "%" + likeSearch + "%";
            userPrePayInfoList = userPrePayInfoMapper.likeSearchByGNameOrUsernameInNoDid(likeSearch, likeSearch);
        }

        List<String> userNameList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> preTimeList = new ArrayList<>();
        List<String> goodsNameList = new ArrayList<>();
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            userNameList.add(userMapper.getRealNameByUid(userPrePayInfo.getUid()));
            preTimeList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
        }

        model.addAttribute("userNameList", userNameList);
        model.addAttribute("preTimeList", preTimeList);
        model.addAttribute("goodsNameList", goodsNameList);

        return userPrePayInfoList;
    }

    @Override
    public List<UserPrePayInfo> getPreInfoByDid(Model model, int uid) {
        List<UserPrePayInfo> userPrePayInfoList = userPrePayInfoMapper.getPrePayInfoByDid(docMapper.getDidByUid(uid));
        List<String> goodsNameList = new ArrayList<>();
        List<String> usernameList = new ArrayList<>();
        List<String> preTimeList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
            usernameList.add(userMapper.getRealNameByUid(userPrePayInfo.getUid()));
            preTimeList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
        }

        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("usernameList", usernameList);
        model.addAttribute("preTimeList", preTimeList);

        return userPrePayInfoList;
    }

    @Override
    public List<UserPrePayInfo> likeSearchInDid(Model model, int uid, String likeSearch) {
        List<UserPrePayInfo> userPrePayInfoList;
        int did = docMapper.getDidByUid(uid);
        if (StringUtils.isNumeric(likeSearch)) {
            likeSearch = "%" + likeSearch + "%";
            userPrePayInfoList = userPrePayInfoMapper.likeSearchByPreNumberInDid(did, likeSearch);
        } else {
            likeSearch = "%" + likeSearch + "%";
            userPrePayInfoList = userPrePayInfoMapper.likeSearchByGNameOrUsernameInDid(did, likeSearch, likeSearch);
        }

        List<String> goodsNameList = new ArrayList<>();
        List<String> usernameList = new ArrayList<>();
        List<String> preTimeList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (UserPrePayInfo userPrePayInfo : userPrePayInfoList) {
            goodsNameList.add(preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid()));
            usernameList.add(userMapper.getRealNameByUid(userPrePayInfo.getUid()));
            preTimeList.add(simpleDateFormat.format(userPrePayInfo.getPretime()));
        }

        model.addAttribute("goodsNameList", goodsNameList);
        model.addAttribute("usernameList", usernameList);
        model.addAttribute("preTimeList", preTimeList);

        return userPrePayInfoList;
    }

    @Override
    @Transactional
    public void userBreakPre(int uid, int pid) {
        if (!userPrePayInfoMapper.userBreakPre(pid)) throw new RuntimeException("违约失败！");
        if (!userMapper.userBreakPre(userMapper.getIfComeByUid(uid) + 1, uid)) throw new RuntimeException("违约失败！");
    }

    @Override
    public UserPrePayInfo getPrePayInfoAndSetUserInfo(Model model, int uid, int pid) {
        UserInfo userInfo = userMapper.getUserInfoByUid(uid);
        UserPrePayInfo userPrePayInfo = userPrePayInfoMapper.getInfoByPid(pid);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String birthday = simpleDateFormat.format(userInfo.getBirthday());
        String preTime = simpleDateFormat.format(userPrePayInfo.getPretime());
        String goodsName = preGoodsMapper.getGoodsNameByGid(userPrePayInfo.getGid());
        String startTime = null;
        if (userPrePayInfo.getStarttime() != null) {
            startTime = simpleDateFormat1.format(userPrePayInfo.getStarttime());
        } else {
            Date start = new Date();
            if (userPrePayInfoMapper.updateStartTime(start, pid)) {
                startTime = simpleDateFormat1.format(start);
            }
        }
        String endTime = null;
        if (userPrePayInfo.getEndtime() != null) {
            endTime = simpleDateFormat1.format(userPrePayInfo.getEndtime());
        }

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("birthday", birthday);
        model.addAttribute("preTime", preTime);
        model.addAttribute("goodsName", goodsName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);


        return userPrePayInfo;
    }

    @Override
    public void updateJiluAndComment(String jiLu, String comment, int pid) {
        if (!userPrePayInfoMapper.updateJiLuAndCommentByPid(jiLu, comment, pid)) throw new RuntimeException("暂存失败");
    }

    @Override
    public void updateEndTime(int pid) {
        if (!userPrePayInfoMapper.updateEndTimeByPid(new Date(), pid)) throw new RuntimeException("设置结束时间失败");
    }

    @Override
    @Transactional
    public void updateTiJianState(int pid) {
        if (!userPrePayInfoMapper.updateTiJianStateByPid(pid)) throw new RuntimeException("设置体检状态失败");
        UserPrePayInfo info = userPrePayInfoMapper.getInfoByPid(pid);
        System.out.println(info.getEndtime());
        if (!userAlreadyInfoMapper.insertAlreadyInfo(info.getUid(), info.getGid(), info.getDid(), info.getPrenumber(), info.getEndtime())) {
            throw new RuntimeException("生成评价失败！");
        }
    }


}
