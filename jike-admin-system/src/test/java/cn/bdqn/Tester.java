package cn.bdqn;

import cn.bdqn.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class  Tester {

    @Autowired
    private SysUserService userService;

    @Value("${server.port}")
    private int port;



    @Test
    void contextLoad(){
    }
}
