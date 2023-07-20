package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.entity.Employee;

import java.util.List;

/**
 * @Date: 2023/7/20
 * @Author: Administrator
 * @ClassName: EmployeeMapper
 * @Description: comment here
 */

public interface EmployeeMapper {

    /**
     * 多条件查询
     * @param employee
     * @return
     */
    List<Employee> select(Employee employee);
}
