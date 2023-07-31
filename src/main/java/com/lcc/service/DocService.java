package com.lcc.service;

import com.lcc.entity.DoctorInfo;
import com.lcc.entity.Notice;
import com.lcc.entity.UserAlreadyInfo;
import com.lcc.entity.UserPrePayInfo;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ClassName: DocService
 * Package: com.lcc.service
 * Description:
 *
 * @Author lcc
 * @Create 2023/5/1 14:42
 * @Version
 */
public interface DocService {

    List<UserAlreadyInfo> getIndexInfo(int uid, Model model);

    DoctorInfo getDocInfo(int uid);

    boolean comparePassword(HttpSession session, String password);

    void updatePassword(HttpSession session, String password);

    void updateDocInfo(int uid, String realName, int age, String gender, int saveAge, String phone);

    Notice getNotice(Model model);

    List<UserPrePayInfo> getPayInfo(Model model);

    void approvePreUser(int uid, int pid);

    List<UserPrePayInfo> likeSearchNoDid(Model model, String likeSearch);

    List<UserPrePayInfo> getPreInfoByDid(Model model, int uid);

    List<UserPrePayInfo> likeSearchInDid(Model model, int uid, String likeSearch);

    void userBreakPre(int uid, int pid);

    UserPrePayInfo getPrePayInfoAndSetUserInfo(Model model, int uid, int pid);

    void updateJiluAndComment(String jiLu, String comment, int pid);

    void updateEndTime(int pid);

    void updateTiJianState(int pid);
}
