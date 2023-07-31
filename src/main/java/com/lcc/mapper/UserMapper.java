package com.lcc.mapper;

import com.lcc.entity.DoctorInfo;
import com.lcc.entity.User;
import com.lcc.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * ClassName: UserMapper
 * Package: com.lcc.mapper
 * Description: user及userinfo表
 *
 * @Author lcc
 * @Create 2023/3/20 18:54
 * @Version
 */

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getInfoByUsername(String username);

    @Options(useGeneratedKeys = true, keyColumn = "uid", keyProperty = "uid")
    @Insert("insert into user (username,role,password) values (#{username},#{role},#{password})")
    boolean setUser(User user);

    @Insert("insert into userinfo (uid,realname,email,birthday,gender,phone) values (#{uid},#{realname},#{email},#{birthday},#{gender},#{phone})")
    boolean setUserInfo(@Param("uid") int uid, @Param("realname") String realname, @Param("email") String email, @Param("birthday") Date birthday, @Param("gender") String gender, @Param("phone") String phone);

    @Select("select * from userinfo where realname = #{realname}")
    UserInfo getUserInfoByRealName(String realname);

    @Delete("delete from user where uid = #{uid}")
    boolean delUser(int uid);

    @Select("select username from user where uid = #{uid}")
    String getUsernameByUid(int uid);

    @Select("select realname from userinfo where uid = #{uid}")
    String getRealNameByUid(int uid);

    @Select("select phone from userinfo where uid = #{uid}")
    String getPhoneByUid(int uid);

    @Select("select count(*) from userinfo")
    Integer getUserInfoTotal();

    @Update("update user set password = #{password} where username = #{username}")
    boolean updatePassword(@Param("password") String password, @Param("username") String username);

    @Select("select * from userinfo where uid = #{uid}")
    UserInfo getUserInfoByUid(int uid);

    @Update("update userinfo set realname = #{realName},email = #{email},birthday = #{birthday},gender = #{gender},phone = #{phone} where uid = #{uid}")
    boolean updateUserInfo(@Param("realName") String realName,
                           @Param("email") String email,
                           @Param("birthday") Date birthday,
                           @Param("gender") String gender,
                           @Param("phone") String phone, @Param("uid") int uid);

    @Select("select nocome from userinfo where uid = #{uid}")
    Integer getNoComeByUid(int uid);

    @Select("select nocome from userinfo where uid = #{uid}")
    Integer getIfComeByUid(int uid);

    @Update("update userinfo set nocome = #{nocome} where uid = #{uid}")
    boolean userBreakPre(@Param("nocome") int nocome, @Param("uid") int uid);
}
