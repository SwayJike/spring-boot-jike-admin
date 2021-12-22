package cn.bdqn;

import cn.bdqn.util.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JikeAdminApplication {


    public static void main(String[] args) {
        SpringContextHolder.setApplicationContext(SpringApplication.run(JikeAdminApplication.class, args));
    }

}
