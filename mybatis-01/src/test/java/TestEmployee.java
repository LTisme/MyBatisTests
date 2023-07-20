import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Date: 2023/7/5
 * @Author: Administrator
 * @ClassName: TestMyBatis
 * @Description:
 */

// 有了lombok就可以只用注解就可以替代日志：private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogger.class);
@Slf4j
public class TestEmployee {
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
        try(SqlSession session = sqlSessionFactory.openSession()){
            try {
                EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
                List<Employee> employeeList = mapper.select(new Employee());
                log.info("Employees are [{}]", employeeList);
            } catch (Exception e){
                log.error("发生了异常", e);
                // 发生异常就回滚
                session.rollback();
            }
        }
    }
}
