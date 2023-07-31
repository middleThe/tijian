package com.lcc.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.sql.DataSource;

/**
 * ClassName: RootConfguration
 * Package: book.manager.config
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/16 16:59
 * @Version
 */
@Configuration
@ComponentScans({
        @ComponentScan("com.lcc.service")
})
@MapperScan("com.lcc.mapper")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class RootConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/tijian");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;

    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置分页的拦截器
        PageInterceptor pageInterceptor = new PageInterceptor();
        //创建插件需要的参数集合
        Properties properties = new Properties();
        //配置数据库方言 为mysql
        properties.setProperty("helperDialect", "mysql");
        //配置分页的合理化数据
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        //将拦截器设置到sqlSessionFactroy中
        Interceptor[] interceptors = new Interceptor[]{pageInterceptor};
        bean.setPlugins(interceptors);
        return bean;
    }

    @Bean
    public TransactionManager transactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
