package com.lcc.mapper;

import com.lcc.entity.Notice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * ClassName: NoticeMapper
 * Package: com.lcc.mapper
 * Description: announcement表
 *
 * @Author lcc
 * @Create 2023/4/6 20:14
 * @Version
 */
@Mapper
public interface NoticeMapper {
    @Insert("insert into announcement (title,content,create_time,role) values (#{title},#{content},#{create_time},#{role})")
    boolean insertNotice(@Param("title") String title, @Param("content") String content, @Param("create_time") Date create_time, @Param("role") String role);

    @Select("select * from announcement where role = 'user' ORDER BY create_time DESC LIMIT 1")
    Notice getNoticeForUser();

    @Select("select * from announcement where role = 'doc' ORDER BY create_time DESC LIMIT 1")
    Notice getNoticeForDoc();
}
