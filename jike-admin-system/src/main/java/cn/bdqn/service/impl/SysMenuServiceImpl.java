package cn.bdqn.service.impl;

import cn.bdqn.mapper.SysMenuMapper;
import cn.bdqn.model.entity.SysMenu;
import cn.bdqn.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> menuList() {
        return menuMapper.menuList();
    }
}
