package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {

    //SqlSessionFactory对象
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * 1、根据xml配置文件（全局配置文件），创建一个SqlSessionFactory对象
     * 有数据源一下运行环境信息
     * 2、sql映射文件，配置了每一个sql，以及sql的封装规则等
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码
     * 1）、根据全局配置文件得到SqlSessionFactory；
     * 2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        //2、获取SqlSession实例，能直接执行已经映射的sql语句
        SqlSession session = sqlSessionFactory.openSession();
        try {
            //参数：sql语句唯一标识，执行sql要用的参数
            Employee employee = session.selectOne("com.atguigu.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);

        } finally {
            session.close();
        }
    }


    @Test
    public void test02() throws IOException {

        //1、获得sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取SqlSession对象
        SqlSession session = sqlSessionFactory.openSession();
        try {
            //3、获得接口实现类对象
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            session.close();
        }
    }


}
