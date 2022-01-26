package cn.bdqn.handler;

import cn.bdqn.common.lang.CommonResult;
import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @title:Jwt身份验证入口点
 * @Author SwayJike
 * @Date:2021/11/28 14:46
 * @Version 1.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        CommonResult failure = CommonResult.failure().setMessage(e.getMessage());
        out.write(JSONUtil.toJsonStr(failure));
        out.flush();
        out.close();
    }
}
