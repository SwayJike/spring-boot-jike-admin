package cn.bdqn.controller;


import cn.bdqn.common.constant.HttpMessageConst;
import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.model.entity.SysRole;
import cn.bdqn.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@RestController
@RequestMapping("/sysRole")
@Api(value = "/sysRole", description = "角色表 前端控制器")
@Slf4j
public class SysRoleController {

    @Autowired
    SysRoleService roleService;

    @ApiOperation(value = "获取角色列表")
    @GetMapping("/roleList/{pageNum}/{pageSize}")
    public CommonResult<SysRole> roleList(@PathVariable Integer pageNum,
                                         @PathVariable Integer pageSize,
                                         @RequestParam(value = "roleName", defaultValue = "")String roleName){
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> roles = roleService.list(new QueryWrapper<SysRole>().like("name", roleName));
        PageInfo<SysRole> pageInfo = new PageInfo<>(roles);
        return CommonResult.success(pageInfo).setMessage(HttpMessageConst.QUERY_SUCCESS_MESSAGE);
    }

    @ApiOperation(value = "添加或修改角色")
    @PostMapping("/saveOrUpdate")
    public CommonResult<Object> saveOrUpdate(SysRole sysRole){
        boolean flag = roleService.saveOrUpdate(sysRole);
        SysRole role;
        log.info(sysRole.toString());
        if (flag){
            String message;
            /*没有更新时间则是第一次添加用户,因为更新时有更新填充策略,而添加没有,此处为投机取巧*/
            if (Objects.isNull(sysRole.getUpdateTime())){
                message = HttpMessageConst.INSERT_SUCCESS_MESSAGE;
            }else {
                message = HttpMessageConst.UPDATE_SUCCESS_MESSAGE;
            }
            role = roleService.getOne(new QueryWrapper<SysRole>().eq("name", sysRole.getName()));
            return CommonResult.success(role).setMessage(message);
        }else {
            return CommonResult.failure();
        }
    }

    @ApiOperation(value = "根据角色Id删除角色")
    @DeleteMapping("/removeRole/{roleId}")
    public CommonResult<Object> removeRole(@PathVariable(value = "roleId") String roleId){
        boolean flag = roleService.removeById(roleId);
        if (flag){
            return CommonResult.success().setMessage(HttpMessageConst.REMOVE_SUCCESS_MESSAGE);
        }else {
            return CommonResult.failure();
        }
    }

    @ApiOperation(value = "根据多个用户Id删除用户")
    @DeleteMapping("/removeSelectedRole")
    public CommonResult<Object> removeSelectedRole(@RequestParam(value = "ids") List<Long> ids){
        boolean flag = roleService.removeByIds(ids);
        if (flag){
            return CommonResult.success().setMessage(HttpMessageConst.REMOVE_SUCCESS_MESSAGE);
        }else {
            return CommonResult.failure();
        }
    }

}
