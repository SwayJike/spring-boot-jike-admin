package cn.bdqn.mapper;

import cn.bdqn.cache.MybatisRedisCache;
import cn.bdqn.model.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> menuList();
}
