package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Date: 2023/7/20
 * @Author: Administrator
 * @ClassName: Dept
 * @Description: comment here
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Integer id;
    private String deptName;
}
