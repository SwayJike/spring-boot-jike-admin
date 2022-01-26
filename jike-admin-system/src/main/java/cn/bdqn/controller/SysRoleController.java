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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
}
