package cn.bdqn.config;

import cn.bdqn.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] URL_WHITELIST = {
            "/login", "/logout", "/druid/**", "/doc.html", "/swagger-resources/**", "/webjars/**", "/v2/**"
    };
    @Autowired
    @Qualifier("loginSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Qualifier("loginFailureHandler")
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier("jwtLogoutSuccessHandler")
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    @Qualifier("jwtAuthenticationEntryPoint")
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    @Qualifier("jwtAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    @Qualifier("userDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    /**
     * 通过auto可以在内存中构建虚拟的用户名和密码
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域攻击 并 禁用session
        http.cors()
                .and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*配置http请求认证相关信息*/
        http.authorizeRequests()
                /*放行一些不需要拦截的请求*/
                .antMatchers(URL_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                /*设置表单的登录处理器*/
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                /*设置表单的登出控制器*/
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .clearAuthentication(true)
                .and()
                /*认证异常处理*/
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                /*添加自定义过滤器*/
                .addFilter(jwtAuthenticationFilter());


        /*记住我功能*/
        http.rememberMe()
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*7)
                .tokenRepository(persistentTokenRepository());

    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        /*jdbcTokenRepository.setCreateTableOnStartup(true);*/
        return jdbcTokenRepository;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() throws RuntimeException{
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }


}
