package com.lcc.mapper;

import com.lcc.entity.UserAlreadyInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: UserAlreadyInfoMapper
 * Package: com.lcc.mapper
 * Description: alreadytijianinfo表
 *
 * @Author lcc
 * @Create 2023/4/3 20:44
 * @Version
 */
@Mapper
public interface UserAlreadyInfoMapper {

    @Select("select * from alreadytijianinfo where comment = 1 order by time desc")
    List<UserAlreadyInfo> getUserAlreadyTiJianInfo();

    @Select("select * from alreadytijianinfo where prenumber like #{prenumber} and comment = 1 order by time desc")
    List<UserAlreadyInfo> getAlreadyInfoLikeSearchByPrenumber(String prenumber);

    @Select("select comment from alreadytijianinfo where prenumber = #{prenumber}")
    Integer getCommentByPreNumber(String prenumber);

    @Update("update alreadytijianinfo set sdegree = #{sdegree},comment = 1 where prenumber = #{prenumber}")
    boolean comment(@Param("prenumber") String prenumber, @Param("sdegree") String sdegree);

    @Insert("insert into alreadytijianinfo  (uid,gid,did,prenumber,time) values (#{uid},#{gid},#{did},#{prenumer},#{time})")
    boolean insertAlreadyInfo(@Param("uid") int uid, @Param("gid") int gid, @Param("did") int did, @Param("prenumer") String prenumer, @Param("time") Date time);

    @Select("select * from alreadytijianinfo where did = #{did} order by time desc")
    List<UserAlreadyInfo> getInfoByDid(int did);

    @Select("SELECT count(*) FROM alreadytijianinfo where did = #{did}")
    Integer getFinishCountByDid(int did);

    @Select("SELECT count(*) FROM alreadytijianinfo where did = #{did} and sdegree = '不满意'")
    Integer getBadCommentByDid(int did);

    @Select("SELECT count(*) FROM alreadytijianinfo where did = #{did} and sdegree = '很满意'")
    Integer getGoodCommentByDid(int did);

}
