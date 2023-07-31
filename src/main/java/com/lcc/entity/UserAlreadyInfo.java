package com.lcc.entity;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: UserAlreadyInfo
 * Package: com.lcc.entity
 * Description: 用户历史评价类
 *
 * @Author lcc
 * @Create 2023/4/3 20:44
 * @Version
 */
@Data
public class UserAlreadyInfo {
    private Integer aid;
    private Integer uid;
    private Integer gid;
    private Integer did;
    private String prenumber;
    private Date time;
    private String sdegree;


}
