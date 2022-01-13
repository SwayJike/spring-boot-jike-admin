package cn.bdqn.controller;


import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.model.entity.SysDept;
import cn.bdqn.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@RestController
@RequestMapping("/sysDept")
@Api(value = "/sysDept", description = "部门 前端控制器")
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @ApiOperation(value = "获取部门列表")
    @GetMapping("/deptList")
    public CommonResult<SysDept> deptList(){
        List<SysDept> deptList = deptService.deptList();
        return CommonResult.success(deptList).clearMessage();
    }

    @ApiOperation(value = "获取部门列表映射")
    @GetMapping("/deptMap")
    public CommonResult<Object> deptMap(){
        /*deptId为key, deptName 为value*/
        Map<Long, String> map = deptService.list().stream().collect(Collectors.toMap(SysDept::getDeptId, SysDept::getName));
        return CommonResult.success(map).clearMessage();
    }

}
