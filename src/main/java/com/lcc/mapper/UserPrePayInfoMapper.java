package com.lcc.mapper;

import com.lcc.entity.UserPrePayInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: UserPrePayInfoMapper
 * Package: com.lcc.mapper
 * Description: prepayinfo表
 *
 * @Author lcc
 * @Create 2023/3/31 20:14
 * @Version
 */
@Mapper
public interface UserPrePayInfoMapper {

    @Select("select * from prepayinfo where ifok = 0")
    List<UserPrePayInfo> getPrePayInfoByNotOk();

    @Update("UPDATE prepayinfo set ifok = 1 where pid = #{pid};")
    boolean toAgree(int pid);

    @Select("select * from prepayinfo where prenumber like #{prenumber} and ifok = 0")
    List<UserPrePayInfo> likeSearchByPreNumber(String prenumber);

    @Select("select count(*) from prepayinfo")
    Integer getPrePayTotal();

    @Select("select * from prepayinfo where uid = #{uid} and iftijian = 1 ORDER BY pretime DESC")
    List<UserPrePayInfo> getUserPrePayInfo(int uid);

    @Select("select * from prepayinfo where pid = #{pid}")
    UserPrePayInfo getInfoByPid(int pid);

    @Select("select pid from prepayinfo where prenumber = #{prenumber}")
    Integer getPidByPreNumber(String prenumber);

    @Insert("insert into prepayinfo (uid,gid,prenumber,pretime) values (#{uid},#{gid},#{prenumber},#{pretime})")
    boolean addPreUser(@Param("uid") int uid, @Param("gid") int gid, @Param("prenumber") String prenumber, @Param("pretime") Date pretime);

    @Select("select * from prepayinfo where uid = #{uid} and iftijian = 0 and pretime >= CURDATE() ORDER BY pretime ")
    List<UserPrePayInfo> getPrePayInfo(int uid);

    @Select("select ifok from prepayinfo where pid = #{pid}")
    Integer getIfOkByPid(int pid);

    @Select("select pretime from prepayinfo where pid = #{pid}")
    Date getPreTimeByPid(int pid);

    @Delete("delete from prepayinfo where pid = #{pid} ")
    boolean removePrePayInfo(int pid);

    @Select("select * from prepayinfo where uid = #{uid} and iftijian = 0 and pretime >= CURDATE() and prenumber like #{likeSearch} ORDER BY pretime DESC")
    List<UserPrePayInfo> likeSearchByPreNumberWithUser(@Param("uid") int uid, @Param("likeSearch") String likeSearch);

    @Select("SELECT * FROM prepayinfo a INNER JOIN pregoodsinfo b on a.gid = b.gid " +
            "where a.uid = #{uid} and a.iftijian = 0 and a.pretime >= CURDATE() and b.goodsname like #{likeSearch} ORDER BY pretime DESC")
    List<UserPrePayInfo> likeSearchByGoodsNameWithUser(@Param("uid") int uid, @Param("likeSearch") String likeSearch);

    @Select("select gid from prepayinfo where uid = #{uid}")
    List<Integer> getGidByUid(int uid);

    @Select("SELECT * FROM prepayinfo where did is null ORDER BY ifok DESC,pretime")
    List<UserPrePayInfo> getPerPayInfoByNoDid();

    @Update("update prepayinfo set did = #{did},ifok = 1 where pid = #{pid}")
    boolean approvePreUser(@Param("did") int did, @Param("pid") int pid);

    @Update("update prepayinfo set did = #{did} where pid = #{pid}")
    boolean responsibleFor(@Param("did") int did, @Param("pid") int pid);

    @Select("select * from prepayinfo where did is null and prenumber like #{prenumber} ORDER BY ifok DESC,pretime")
    List<UserPrePayInfo> likeSearchByPreNumberInNoDid(String prenumber);

    @Select("select * from prepayinfo a INNER JOIN pregoodsinfo b on a.gid = b.gid INNER JOIN userinfo c on a.uid = c.uid \n" +
            "HAVING did is null and (goodsname like #{goodsname} or c.realname like #{realname})  ORDER BY ifok DESC,pretime")
    List<UserPrePayInfo> likeSearchByGNameOrUsernameInNoDid(@Param("goodsname") String goodsname, @Param("realname") String realname);

    @Select("select * from prepayinfo where did = #{did} and pretime >= CURDATE() order by pretime ,iftijian")
    List<UserPrePayInfo> getPrePayInfoByDid(int did);

    @Select("select * from prepayinfo a INNER JOIN pregoodsinfo b on a.gid = b.gid INNER JOIN userinfo c on a.uid = c.uid " +
            "HAVING did = #{did} and pretime >= CURDATE() " +
            "and (goodsname like #{goodsname} or c.realname like #{realname}) ORDER BY pretime ,iftijian")
    List<UserPrePayInfo> likeSearchByGNameOrUsernameInDid(@Param("did") int did, @Param("goodsname") String goodsname, @Param("realname") String realname);

    @Select("select * from prepayinfo where did = #{did} and pretime >= CURDATE() and prenumber like #{prenumber} ORDER BY pretime ,iftijian")
    List<UserPrePayInfo> likeSearchByPreNumberInDid(@Param("did") int did, @Param("prenumber") String prenumber);

    @Update("update prepayinfo set ifbreak = 1 where pid = #{pid}")
    boolean userBreakPre(int pid);

    @Update("update prepayinfo set starttime = #{starttime} where pid = #{pid}")
    boolean updateStartTime(@Param("starttime") Date starttime, @Param("pid") int pid);

    @Update("update prepayinfo set jilu = #{jilu},comment = #{comment} where pid = #{pid}")
    boolean updateJiLuAndCommentByPid(@Param("jilu") String jilu, @Param("comment") String comment, @Param("pid") int pid);

    @Update("update prepayinfo set endtime = #{endtime} where pid = #{pid}")
    boolean updateEndTimeByPid(@Param("endtime") Date endtime, @Param("pid") int pid);

    @Update("update prepayinfo set iftijian = 1 where pid = #{pid}")
    boolean updateTiJianStateByPid(int pid);
}
