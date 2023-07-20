package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2023/7/20
 * @Author: Administrator
 * @ClassName: Dept
 * @Description: Dept和 Employee 都维护了对方的对象，双向维护
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Integer id;
    private String deptName;

    private List<Employee> employees;
}
