package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2023/7/5
 * @Author: Administrator
 * @ClassName: User
 * @Description:
 */

// 使用Lombok来简化getter setter 写法
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final Long serialVersionUID = 1L;

    private int id;
    private String username;
    private String password;
}
