package cn.bdqn.mapper;

import cn.bdqn.model.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> menuList();

}
