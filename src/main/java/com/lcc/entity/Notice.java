package com.lcc.entity;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: Notice
 * Package: com.lcc.entity
 * Description: 通知类
 *
 * @Author lcc
 * @Create 2023/4/6 20:11
 * @Version
 */
@Data
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Date create_time;
    private String role;
}
