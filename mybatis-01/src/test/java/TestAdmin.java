import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.Admin;
import org.example.mapper.AdminMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Date: 2023/7/6
 * @Author: Administrator
 * @ClassName: TestAdmin
 * @Description:
 */

@Slf4j
public class TestAdmin {
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
    public void testSelect() {
        // 使用 SqlSession 创建 Mapper
        try(SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            // 调用 Mapper 的方法

            AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
            List<Admin> allAdmins = mapper.findAllAdmins();

            log.debug("allAdmins: {}", allAdmins);
        }
    }
}
