package com.lcc.entity;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: UserPrePayInfo
 * Package: com.lcc.entity
 * Description: 商品预约类
 *
 * @Author lcc
 * @Create 2023/3/31 20:11
 * @Version
 */
@Data
public class UserPrePayInfo {
    private Integer pid;
    private Integer uid;
    private Integer gid;
    private Integer did;
    private String prenumber;
    private Integer ifok;
    private Date pretime;
    private Date starttime;
    private Date endtime;
    private Integer iftijian;
    private String jilu;
    private String comment;
    private Integer ifbreak;
}
