package com.lcc.entity;

import lombok.Data;

/**
 * ClassName: DoctorInfo
 * Package: com.lcc.entity
 * Description: 医生信息类
 *
 * @Author lcc
 * @Create 2023/3/27 18:11
 * @Version
 */
@Data
public class DoctorInfo {
    private Integer did;
    private Integer uid;
    private String realname;
    private Integer age;
    private String gender;
    private Integer saveage;
    private String phone;
    private String title;

}
