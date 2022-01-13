package cn.bdqn.service;

import cn.bdqn.model.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
public interface SysDeptService extends IService<SysDept> {

    List<SysDept> deptList();
}
