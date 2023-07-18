import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2023/7/5
 * @Author: Administrator
 * @ClassName: TestMyBatis
 * @Description:
 */

// 有了lombok就可以只用注解就可以替代日志：private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogger.class);
@Slf4j
public class TestMyBatis {
    SqlSessionFactory sqlSessionFactory = null;

    // 在执行Test之前都会先执行Before，这样可以把一些重复性的代码统一放到这里来处理
    @Before
    public void before() throws IOException {
        // 从 XML 中构建 SqlSessionFactory，官网中还要求我们配置这个xml文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }


    @Test
    public void testSqlSession() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            // select 语句中的statement不是真实的sql语句，只是声明，具体的sql语句在 mybatis-config.xml 中的mapper.xml配置文件中已经声明好了
            // 比如下面这句话就是说，声明使用user命名空间的select标签的sql语句
//            List<User> users = session.selectList("user.select");
            List<User> users = session.selectList("selectAll");
            log.debug("sqlSessionFactory is [{}]", users);

        }
    }

    // MyBatis 提供了动态 Mapper 的方式来更好的执行sql语句
    @Test
    public void testMapper() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            // 获得一个代理对象，使用的是JDK的Proxy类
            UserMapper mapper = session.getMapper(UserMapper.class);
            // 这个代理对象实现了被代理的接口的具体方法
            List<User> users = mapper.selectAll();
            log.debug("users is [{}]", users);
        }
    }

    // MyBatis 提供了动态 Mapper 的方式来更好的执行sql语句
    @Test
    public void testMapperFindById() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectOne(4);
            log.debug("user is [{}]", user);
        }
    }

    @Test
    public void testMapperFindByMultiParam() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.select("xiaoli", "abcdef");
            log.debug("user is [{}]", user);
        }
    }

    // 传入一个实例对象，也能查询
    @Test
    public void testMapperFindByUser() {
        try(SqlSession session = sqlSessionFactory.openSession()){
//            User user1 = new User(1, "xiaozhang", "123456");
            User user1 = new User(null, "xiaozhang", "123456");
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectByUser(user1);
            log.debug("user is [{}]", user);
        }
    }

    // 甚至传入一个对象集合，也能做到查询
    @Test
    public void testMapperFindByMap() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>(2);
            map.put("username", "xiaozhang");
            map.put("password", "123456");
            User user = mapper.selectByMap(map);

            log.debug("user is [{}]", user);
        }
    }

    // 进行username的模糊查询
    @Test
    public void testMapperFindByUsername() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectByUsername("xiao");

            log.debug("users is [{}]", users);
        }
    }

    @Test
    public void testMapperInsert() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            try {
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user = new User(4, "xiaobai", "12121");
                int affectedrows = mapper.insert(user);
                log.debug("Affected Rows are [{}]", affectedrows);
                // 对数据库有变动的操作，必须要手动提交事务
                session.commit();
            } catch (Exception e){
                log.error("发生了异常", e);
                // 发生异常就回滚
                session.rollback();
            }
        }
    }

    // 如果不想用手动提交，则需要在openSession里将autocommit置为true
    @Test
    public void testMapperUpdate() {
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = new User(4, "xiaolan", "1111");
            int affectedrows = mapper.update(user);

            log.debug("Affected Rows are [{}]", affectedrows);
        }
    }

    @Test
    public void testMapperDelete() {
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            UserMapper mapper = session.getMapper(UserMapper.class);
            int affectedrows = mapper.delete(4);

            log.debug("Affected Rows are [{}]", affectedrows);
        }
    }
}
