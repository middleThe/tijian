package com.lcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: User
 * Package: com.lcc.entity
 * Description: 账号类
 *
 * @Author lcc
 * @Create 2023/3/20 20:35
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer uid;

    private String username;

    private String role;

    private String password;
}
