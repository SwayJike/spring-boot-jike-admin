package cn.bdqn.controller;


import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.model.entity.SysUser;
import cn.bdqn.service.SysUserService;
import cn.bdqn.util.ObjectMapperUtils;
import cn.bdqn.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
@Api(value = "/sysUser", description = "系统用户 前端控制器")
public class SysUserController {

    private static final String key = "user:list";

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisUtils redisUtils;


    @ApiOperation(value = "测试1")
    @GetMapping("/test")
    public CommonResult<SysUser> test(){
        if (!redisUtils.hasKey(key)) {
            log.info("设置缓存");
            List<SysUser> users = userService.list();
            String s = ObjectMapperUtils.objToJson(users);
            redisUtils.set(key, s, 120);
            return CommonResult.success(users);
        }
        log.info("从缓存获取");
        return CommonResult.success(ObjectMapperUtils.jsonToObj(redisUtils.get(key), SysUser[].class));
    }

    @ApiOperation(value = "test2")
    @GetMapping("/test02")
    public CommonResult<SysUser> test02(){
        return CommonResult.success(userService.list());
    }

}
