package cn.bdqn.mapper;

import cn.bdqn.cache.MybatisRedisCache;
import cn.bdqn.model.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    List<SysDept> deptList();
}
