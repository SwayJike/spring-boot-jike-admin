package cn.bdqn.handler;

import cn.bdqn.common.constant.HttpMessageConst;
import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.util.JwtUtils;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @title:登录成功处理程序
 * @Author SwayJike
 * @Date:2021/11/27 23:38
 * @Version 1.0
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        /*生成jwt 并放到请求头中*/
        String jwt = jwtUtils.generateJwt(auth.getName());
        response.setHeader(jwtUtils.getHeader(), jwt);
        CommonResult success = CommonResult.success().setMessage(HttpMessageConst.LOGIN_SUCCESS_MESSAGE);
        out.write(JSONUtil.toJsonStr(success));
        out.flush();
        out.close();

    }
}
