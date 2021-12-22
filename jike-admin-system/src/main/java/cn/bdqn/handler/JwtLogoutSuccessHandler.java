package cn.bdqn.handler;

import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.util.JwtUtils;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @title:Jwt注销成功处理程序
 * @Author SwayJike
 * @Date:2021/11/28 14:29
 * @Version 1.0
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setHeader(jwtUtils.getHeader(), "");
        CommonResult success = CommonResult.success();
        out.write(JSONUtil.toJsonStr(success));
        out.flush();
        out.close();
    }
}
