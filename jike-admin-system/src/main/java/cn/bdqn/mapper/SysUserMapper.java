package cn.bdqn.mapper;

import cn.bdqn.model.entity.SysUser;
import cn.bdqn.cache.MybatisRedisCache;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@Mapper
@CacheNamespace(implementation= MybatisRedisCache.class)
public interface SysUserMapper extends BaseMapper<SysUser> {

}
