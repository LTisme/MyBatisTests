package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Admin;

import java.util.List;

/**
 * @Date: 2023/7/6
 * @Author: Administrator
 * @ClassName: AdminMapper
 * @Description: MyBatis也支持使用注解来实现数据库的crud操作，
 *               使用@Select查询数据库，使用@Insert、@Update、@Delete修改数据库
 *               把写在xml文件中的语句直接写在注解上
 *               当然，也需要在mybatis-config.xml中配置该mapper类，注意是<mapper class="org.example.mapper.AdminMapper"/>
 */

public interface AdminMapper {

    /**
     * 保存管理员
     * @param admin
     * @return
     */
    @Insert("insert into admin (username,password) values (#{username},#{password})")
    int saveAdmin(Admin admin);

    /**
     * 更新管理员
     * @param admin
     * @return
     */
    @Update("update admin set username=#{username} , password=#{password} where id = #{id}")
    int updateAdmin(Admin admin);

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @Delete("delete from admin where id=#{id}")
    int deleteAdmin(int id);

    /**
     * 根据id查找管理员
     * @param id
     * @return
     */
    @Select("select id,username,password from admin where id=#{id}")
    Admin findAdminById(@Param("id") int id);

    /**
     * 查询所有的管理员
     * @return
     */
    @Select("select id,username,password from admin")
    List<Admin> findAllAdmins();

}

