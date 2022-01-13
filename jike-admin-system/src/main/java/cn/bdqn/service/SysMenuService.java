package cn.bdqn.service;

import cn.bdqn.model.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> menuList();
}
