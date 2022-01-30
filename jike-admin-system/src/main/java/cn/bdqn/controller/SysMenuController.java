package cn.bdqn.controller;


import cn.bdqn.common.constant.HttpMessageConst;
import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.model.entity.SysMenu;
import cn.bdqn.service.SysMenuService;
import cn.bdqn.util.ObjectMapperUtils;
import cn.bdqn.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单 前端控制器
 * </p>
 *
 * @author SwayJike
 * @since 2021-12-14
 */
@Slf4j
@RestController
@RequestMapping("/sysMenu")
@Api(value = "/sysMenu", description = "系统菜单 前端控制器")
public class SysMenuController {

    private static final String key = "menu:list";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysMenuService menuService;

    @GetMapping("/menuList")
    @ApiOperation("获取菜单列表")
    public CommonResult<SysMenu> menuList(){
        if (!redisUtils.hasKey(key)){
            log.info("设置菜单列表的缓存");
            List<SysMenu> menus = menuService.menuList();
            String s = ObjectMapperUtils.objToJson(menus);
            redisUtils.set(key, s, 120);
            return CommonResult.success(menus).setMessage(HttpMessageConst.QUERY_SUCCESS_MESSAGE);
        }
        log.info("从redis缓存获取菜单列表");
        return CommonResult.success(ObjectMapperUtils.jsonToObj(redisUtils.get(key), SysMenu[].class))
                .setMessage(HttpMessageConst.QUERY_SUCCESS_MESSAGE);
    }

    @GetMapping("/menus")
    @ApiOperation("获取菜单列表无子菜单")
    public CommonResult<SysMenu> menus(){
        List<SysMenu> menus = menuService.list();
        return CommonResult.success(menus).setMessage(HttpMessageConst.QUERY_SUCCESS_MESSAGE);
    }
}
