package com.lcc.mapper;

import com.lcc.entity.DoctorInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ClassName: DocMapper
 * Package: com.lcc.mapper
 * Description: docinfo表
 *
 * @Author lcc
 * @Create 2023/3/27 18:15
 * @Version
 */
public interface DocMapper {
    @Select("select * from doctorinfo")
    List<DoctorInfo> getDoctorInfo();

    @Insert("insert into doctorinfo (uid,realname,age,gender,saveage,phone,title) values (#{uid},#{realname},#{age},#{gender},#{saveage},#{phone},#{title})")
    boolean addDoctor(@Param("uid") int uid, @Param("realname") String realName, @Param("age") int age, @Param("gender") String gender, @Param("saveage") int saveAge, @Param("phone") String phone, @Param("title") String title);

    @Select("select * from doctorinfo where realname = #{realname}")
    DoctorInfo getDoctorInfoByRealName(String realname);

    @Select("select * from doctorinfo where realname like #{realname}")
    List<DoctorInfo> getDoctorInfoByRealNameLike(@Param("realname") String realname);

    @Select("select * from doctorinfo where did like #{did}")
    List<DoctorInfo> getDoctorInfoByDidLike(@Param("did") String did);

    @Delete("delete from doctorinfo where did = #{did}")
    boolean delDocInfo(int did);

    @Select("select uid from doctorinfo where did = #{did}")
    int getDocUidByDid(int did);

    @Update("update doctorinfo set realname = #{realname},age = #{age},gender = #{gender},saveage = #{saveage},phone = #{phone},title = #{title} where did = #{did}")
    boolean updateDocInfo(@Param("realname") String realname, @Param("age") int age, @Param("gender") String gender, @Param("saveage") int saveage, @Param("phone") String phone, @Param("title") String title, @Param("did") int did);

    @Select("select realname from doctorinfo where did = #{did}")
    String getRealNameByDid(int did);

    @Select("select count(*) from doctorinfo")
    Integer getDoctorTotal();

    @Select("select * from doctorinfo where uid = #{uid}")
    DoctorInfo getDocInfoByUid(int uid);

    @Update("update doctorinfo set realname = #{realname},age = #{age},gender = #{gender},saveage = #{saveage},phone = #{phone} where uid = #{uid}")
    boolean updateDocInfoByUid(@Param("realname") String realname, @Param("age") int age, @Param("gender") String gender, @Param("saveage") int saveage, @Param("phone") String phone, @Param("uid") int uid);

    @Select("select did from doctorinfo where uid = #{uid}")
    Integer getDidByUid(int uid);
}
