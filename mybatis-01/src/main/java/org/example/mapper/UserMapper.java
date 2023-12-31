package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Date: 2023/7/6
 * @Author: Administrator
 * @ClassName: UserMapper
 * @Description: MyBatis 可以给 接口 做动态代理，可以先去看看impl包下静态代理是怎么做的
 *               看完，总结MyBatis怎么做：写一个接口、写一个xml，就OK了
 */

public interface UserMapper {
    /**
     * 如果不给参数加注解的抽象方法，并不能正确执行
     * 只有使用MyBatis的 @Param 给参数注释，才能正确执行
     * @param username
     * @param password
     * @return
     */
    User select(@Param("username") String username, @Param("password") String password);

    List<User> selectAll();

    /**
     * 那如果我只传入一个user对象，可以查询吗？
     * 可以的，因为MyBatis可以通过反射，获取相应的成员变量，然后将其赋值到对应Mapper中的占位符
     * @param user is a param
     * @return User
     */
    User selectByUser(User user);


    /**
     * 添加用户
     * @param user is a param
     * @return int 插入的数量
     */
    int insert(User user);

    /**
     * 实现动态SQL批量插入
     * @param users
     * @return
     */
    int insertBatch(@Param("users") List<User> users);

    /**
     * 修改用户的值
     * @param user
     * @return int
     */
    int update(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 输入多个id来删除用户，通过动态SQL来实现，如果不用@param注解的话，就会导致sql语句识别不出这个
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List<Integer> ids);
}
