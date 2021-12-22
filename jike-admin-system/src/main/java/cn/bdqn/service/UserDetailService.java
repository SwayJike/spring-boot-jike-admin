package cn.bdqn.service;

import cn.bdqn.model.entity.SysUser;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService userService;

    /**
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     *  抛出的异常信息最终在 LoginFailureHandler处理
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(StrUtil.isEmpty(s)){
            throw new UsernameNotFoundException("账号不能为空....");
        }
        SysUser sysUser;
        sysUser = userService.getOne(new QueryWrapper<SysUser>().eq("username",s));
        if (sysUser == null) {
            throw new UsernameNotFoundException(String.format("%s这个账号不存在",s));
        }
        return new User(s, sysUser.getPassword(),
                getAuthorities());
    }

    /**  * 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤     * @param    * @return   */
    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }




}
