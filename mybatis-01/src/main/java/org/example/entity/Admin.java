package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2023/7/6
 * @Author: Administrator
 * @ClassName: Admin
 * @Description: 用来测试使用注解替代xml文件的开发
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin {

    private static final Long serialVersionUID = 1L;

    private int id;
    private String username;
    private String password;
}
