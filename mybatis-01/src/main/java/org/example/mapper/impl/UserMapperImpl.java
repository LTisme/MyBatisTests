package org.example.mapper.impl;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.User;
import org.example.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Date: 2023/7/6
 * @Author: Administrator
 * @ClassName: UserMapperImpl
 * @Description: 实现UserMapper接口，明白自己写的是静态代理，
 *
 *               最终你会发现：前面全是重复性代码，你只需要知道接口的方法名、接口的方法参数、接口方法的返回值，
 *               再配合相应的Mapper.xml的标签，就可以让MyBatis知道，要去配置文件中找到要执行的sql语句
 *
 *               所以！这个实现类都可以不写，直接看测试中的 testMapper ，让session.getMapper() 知道是哪个接口即可，它会根据方法、参数、返回值，自动代理
 */

//public class UserMapperImpl implements UserMapper {
//    @Override
//    public List<User> selectAll() {
//        // 从 XML 中构建 SqlSessionFactory，官网中还要求我们配置这个xml文件
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = null;
//        try {
//            inputStream = Resources.getResourceAsStream(resource);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//
//        try(SqlSession session = sqlSessionFactory.openSession()){
//            // select 语句中的statement不是真实的sql语句，只是声明，具体的sql语句在 mybatis-config.xml 中的mapper.xml配置文件中已经声明好了
//            // 比如下面这句话就是说，声明使用UserMapper 接口命名空间的select标签的sql语句
//            List<User> users = session.selectList(UserMapper.class.getName() + "." + UserMapperImpl.class.getDeclaredMethod("selectAll").getName());
//            System.out.println(users);
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
//
//    public static void main(String[] args) {
//        UserMapperImpl userMapper = new UserMapperImpl();
//        userMapper.selectAll();
//    }
//}
