package cn.bdqn.filter;

import cn.bdqn.util.JwtUtils;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title:JwtAuthenticationFilter
 * @Author SwayJike
 * @Date:2021/11/28 15:04
 * @Version 1.0
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(AuthenticationManager manager){
        super(manager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            /*未认证则继续往下走, security还是会拦截未认证的请求*/
            chain.doFilter(request,response);
            return;
        }

        Claims claim = jwtUtils.getJwtClaim(jwt);
        if (claim == null) {
            throw new JwtException("jwt 异常");
        }
        if (jwtUtils.isExpired(claim)){
            throw new JwtException("jwt 已过期");
        }
        /*设置用户的权限等信息*/
        String username = claim.getSubject();
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, null , null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }
}
