package com.lcc.entity;

import lombok.Data;

/**
 * ClassName: PreGoodsInfo
 * Package: com.lcc.entity
 * Description: 商品类
 *
 * @Author lcc
 * @Create 2023/4/8 9:41
 * @Version
 */
@Data
public class PreGoodsInfo {
    private Integer gid;
    private String goodsname;
    private String pic;
    private Double price;
    private String goodsinfo;
    private String attentioninfo;
    private String forage;
    private String forpeople;
    private String classes;
    private String projectinfo;
    private Integer count;
    private Integer ifshelf;
}
