package cn.bdqn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class SimpleTest {

    @Test
    void main(){
        log.debug(System.getProperty("os.name"));
        log.debug(System.getProperty("os.arch"));
    }



}
