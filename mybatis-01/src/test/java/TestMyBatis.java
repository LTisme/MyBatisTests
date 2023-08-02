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
            List<User> users = session.selectList("org.example.mapper.UserMapper.select");
            log.debug("sqlSessionFactory is [{}]", users);

        }
    }

    @Test
    public void testMapperSelect() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            // 由于缓存机制的存在，就会发现，它不再去数据库去取数据了，而是把数据放置在一级缓存里面，这样就不用去数据层去取数据了
            // 所以你不会再看到 Preparing: select * from user 了
            List<User> users2 = mapper.selectAll();
            log.info("Users are [{}]", users);
            log.info("Users2 are [{}]", users2);
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

    @Test
    public void testMapperInsert() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            try {
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user = new User(null, "xiaoqing", "12121");
                int affectedrows = mapper.insert(user);
                log.info("Affected Rows are [{}]", affectedrows);
                // 如果不是用获取自增的属性的话，这里的id不会受影响
                log.info("Affected Rows' id is [{}]", user.getId());
                // 对数据库有变动的操作，必须要手动提交事务
                session.commit();
            } catch (Exception e){
                log.error("发生了异常", e);
                // 发生异常就回滚
                session.rollback();
            }
        }
    }

    @Test
    public void testMapperInsertBatch() {
        try(SqlSession session = sqlSessionFactory.openSession()){
            try {
                UserMapper mapper = session.getMapper(UserMapper.class);
                List<User> users = List.of(new User(null, "xiaobai", "123"),
                        new User(null, "xiaoli", "1234"),
                        new User(null, "xiaozhang", "12345"),
                        new User(null, "xiaohuang", "123456"));
                int affectedrows = mapper.insertBatch(users);
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
            User user = new User(null, "xiaolan", "1111");
            int affectedrows = mapper.update(user);

            log.debug("Affected Rows are [{}]", affectedrows);
        }
    }

    @Test
    public void testMapperDelete() {
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            UserMapper mapper = session.getMapper(UserMapper.class);
            int affectedrows = mapper.delete(8);

            log.debug("Affected Rows are [{}]", affectedrows);
        }
    }

    @Test
    public void testMapperDeleteByIds() {
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            UserMapper mapper = session.getMapper(UserMapper.class);
            int affectedrows = mapper.deleteByIds(List.of(5,6,7));

            log.debug("Affected Rows are [{}]", affectedrows);
        }
    }
}
