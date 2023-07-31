package com.lcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: UserInfo
 * Package: com.lcc.entity
 * Description: 用户信息类
 *
 * @Author lcc
 * @Create 2023/3/23 16:51
 * @Version
 */
@Data
public class UserInfo {
    private Integer oid;
    private Integer uid;
    private String realname;
    private String email;
    private Date birthday;
    private String gender;
    private String phone;
    private Integer nocome;

}
