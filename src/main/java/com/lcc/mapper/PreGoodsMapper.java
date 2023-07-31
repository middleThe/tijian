package com.lcc.mapper;

import com.lcc.entity.PreGoodsInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ClassName: GoodsMapper
 * Package: com.lcc.mapper
 * Description: pregoodsinfo表
 *
 * @Author lcc
 * @Create 2023/4/1 9:45
 * @Version
 */
@Mapper
public interface PreGoodsMapper {
    @Select("select goodsname from pregoodsinfo where gid = #{gid}")
    String getGoodsNameByGid(int gid);

    @Select("select * from pregoodsinfo")
    List<PreGoodsInfo> getAllProducts();

    @Select("select * from pregoodsinfo where goodsname like #{likeSearch}")
    List<PreGoodsInfo> getProductsByLikeSearch(String likeSearch);

    @Select("select * from pregoodsinfo ORDER BY count DESC LIMIT 5")
    List<PreGoodsInfo> getTop5Product();

    @Delete("delete from pregoodsinfo where gid = #{gid}")
    boolean deleteProductByGid(int gid);

    @Select("select * from pregoodsinfo where gid = #{gid}")
    PreGoodsInfo getInfoByGid(int gid);

    @Update("update pregoodsinfo set " +
            "price = #{price}," +
            "classes = #{classes}," +
            "goodsinfo = #{goodsinfo}," +
            "attentioninfo = #{attentioninfo}," +
            "forage = #{forage} " +
            "where goodsname = #{goodsname}")
    boolean updatePreGoodsInfoByGoodsName(@Param("price") double price, @Param("classes") String classes, @Param("goodsinfo") String goodsinfo, @Param("attentioninfo") String attentioninfo, @Param("goodsname") String goodsname, @Param("forage") String forage);

    @Insert("insert into pregoodsinfo (goodsname,pic,price,goodsinfo,attentioninfo,forage,forpeople,classes,projectinfo) " +
            "values (#{goodsname},#{pic},#{price},#{goodsinfo},#{attentioninfo},#{forage},#{forpeople},#{classes},#{projectinfo})")
    boolean addGoods(@Param("goodsname") String goodsname,
                     @Param("pic") String pic,
                     @Param("price") double price,
                     @Param("goodsinfo") String goodsinfo,
                     @Param("attentioninfo") String attentioninfo,
                     @Param("forage") String forage,
                     @Param("forpeople") String forpeople,
                     @Param("classes") String classes,
                     @Param("projectinfo") String projectinfo);


    @Select("select ifshelf from pregoodsinfo where gid = #{gid}")
    int getIfShelfByGid(int gid);

    @Update("update pregoodsinfo set ifshelf = #{ifshelf} where gid = #{gid}")
    boolean upOrDownTheShelf(@Param("ifshelf") int ifshelf, @Param("gid") int gid);

    @Select("select * from pregoodsinfo where ifshelf = #{ifshelf}")
    List<PreGoodsInfo> getAllGoodsByIfShelf(int ifshelf);

    @Select("select count(*) from pregoodsinfo")
    Integer getGoodsTotal();

    @Select("select * from pregoodsinfo where ifshelf = 1")
    List<PreGoodsInfo> getProductShelf();

    @Select("select * from pregoodsinfo where price <= 200 and ifshelf = 1")
    List<PreGoodsInfo> getGoodsPriceSmall200();

    @Select("select * from pregoodsinfo where price > 200 and ifshelf = 1")
    List<PreGoodsInfo> getGoodsPriceBig200();

    @Select("select * from pregoodsinfo where classes = #{classes} and ifshelf = 1")
    List<PreGoodsInfo> getGoodsByClasses(String classes);

    @Select("select * from pregoodsinfo where forpeople = #{forpeople} and ifshelf = 1")
    List<PreGoodsInfo> getGoodsByForPeople(String forpeople);

    @Select("select count from pregoodsinfo where gid = #{gid}")
    Integer getCountByGid(int gid);

    @Update("update pregoodsinfo set count = #{count} where gid = #{gid}")
    boolean addGoodsCount(@Param("count") int count, @Param("gid") int gid);

    @Select("select price from pregoodsinfo where gid = #{gid}")
    Double getPriceByGid(int gid);
}
