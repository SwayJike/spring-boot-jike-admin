package cn.bdqn.handler;

import cn.bdqn.common.lang.CommonResult;
import cn.hutool.json.JSONUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @title:拒绝访问处理程序
 * @Author SwayJike
 * @Date:2021/11/28 14:54
 * @Version 1.0
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        CommonResult failure = CommonResult.failure(HttpStatus.FORBIDDEN).setMessage(e.getMessage());
        out.write(JSONUtil.toJsonStr(failure));
        out.flush();
        out.close();
    }
}
