package org.example.mapper;

import org.example.entity.Dept;

import java.util.List;

/**
 * @Date: 2023/7/20
 * @Author: Administrator
 * @InterfaceName: DeptMapper
 * @Description: comment here
 */

public interface DeptMapper {
    List<Dept> select(Dept dept);
}
