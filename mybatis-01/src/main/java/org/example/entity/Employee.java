package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2023/7/20
 * @Author: Administrator
 * @ClassName: Employee
 * @Description: comment here
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private static final Long serialVersionUID = 1L;
    private Integer id;
    private String employeeName;
    private String deptName;
    private Integer deptId;
}
