package com.lcc.config;

import com.lcc.entity.User;
import com.lcc.mapper.UserMapper;
import com.lcc.service.impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * ClassName: SecurityConfiguration
 * Package: com.lcc.config
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/20 18:44
 * @Version
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    UserAuthService authService;

    @Resource
    PersistentTokenRepository repository;

    @Resource
    UserMapper userMapper;

    @Bean
    public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();  //使用基于JDBC的实现
        repository.setDataSource(dataSource);   //配置数据源
//        repository.setCreateTableOnStartup(true);   //启动时自动创建用于存储Token的表（建议第一次启动之后删除该行）
        return repository;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authService)   //使用自定义的Service实现类进行验证
                .passwordEncoder(new BCryptPasswordEncoder());   //依然使用BCryptPasswordEncoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**", "/api/share/**", "/page/share/**").permitAll()//静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
                .antMatchers("/page/admin/**", "/api/admin/**").hasRole("admin")
                .antMatchers("/page/user/**", "/api/user/**").hasRole("user")
                .antMatchers("/page/doc/**", "/api/doc/**").hasRole("doc")
                .anyRequest().hasAnyRole("user", "admin", "doc")
                .and()
                .formLogin()       //配置Form表单登陆
                .loginPage("/page/share/login")       //登陆页面地址（GET）
                .loginProcessingUrl("/api/share/doLogin")    //form表单提交地址（POST）
                .successHandler(this::onAuthenticationSuccess)  //登陆成功后跳转的页面，也可以通过Handler实现高度自定义
                .failureUrl("/page/share/login-error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/share/logout")    //退出登陆的请求地址
                .logoutSuccessUrl("/page/share/login")
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(60 * 60 * 24 * 7);
    }

    private void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        System.out.println(authentication.getName());
        User user = userMapper.getInfoByUsername(authentication.getName());
        session.setAttribute("user", user);
        if (user.getRole().equals("admin")) httpServletResponse.sendRedirect("/page/admin/index");
        else if (user.getRole().equals("doc")) httpServletResponse.sendRedirect("/page/doc/index");
        else httpServletResponse.sendRedirect("/page/user/index");
    }

}
