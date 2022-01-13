package cn.bdqn.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.bdqn.common.constant.HttpMessageConst;
import cn.bdqn.common.lang.CommonResult;
import cn.bdqn.model.entity.SysUser;
import cn.bdqn.service.SysUserService;
import cn.bdqn.util.ExcelUtils;
import cn.bdqn.util.ObjectMapperUtils;
import cn.bdqn.util.RedisUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

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
    private static final String TITLE = "用户列表";
    private static final String SHEET_NAME = "用户工作表";

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

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/userList/{pageNum}/{pageSize}")
    public CommonResult<SysUser> userList(@PathVariable(value = "pageNum") String pageNum,
                                          @PathVariable(value = "pageSize") String pageSize,
                                          @RequestParam(value = "username", defaultValue = "", required = false)String username){
        PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        List<SysUser> users = userService.list(wrapper);
        PageInfo<SysUser> pageInfo = new PageInfo<>(users);
        return CommonResult.success(pageInfo)
                .setMessage(HttpMessageConst.QUERY_SUCCESS_MESSAGE);
    }

    @ApiOperation(value = "添加或修改用户")
    @PostMapping("/saveOrUpdate")
    public CommonResult<SysUser> saveOrUpdate(SysUser sysUser){
        boolean flag = userService.saveOrUpdate(sysUser);
        SysUser user;
        if (flag){
            String message;
            /*没有更新时间则是第一次添加用户,因为更新时有更新填充策略,而添加没有,此处为投机取巧*/
            if (Objects.isNull(sysUser.getUpdateTime())){
                message = HttpMessageConst.INSERT_SUCCESS_MESSAGE;
            }else {
                message = HttpMessageConst.UPDATE_SUCCESS_MESSAGE;
            }
            user = userService.getOne(new QueryWrapper<SysUser>().eq("username", sysUser.getUsername()));
            return CommonResult.success(user).setMessage(message);
        }else {
            return CommonResult.failure();
        }
    }

    @ApiOperation(value = "获取当前操作员用户名")
    @GetMapping("/userInfo")
    public CommonResult<String> userInfo(Principal principal){
        return CommonResult.success(principal.getName()).clearMessage();
    }

    @ApiOperation(value = "根据用户Id删除用户")
    @DeleteMapping("/removeUser/{userId}")
    public CommonResult<Object> removeUser(@PathVariable String userId){
        boolean flag = userService.removeById(userId);
        if (flag){
            return CommonResult.success().setMessage(HttpMessageConst.REMOVE_SUCCESS_MESSAGE);
        }else {
            return CommonResult.failure();
        }
    }

    @ApiOperation(value = "根据多个用户Id删除用户")
    @DeleteMapping("/removeSelectedUser")
    public CommonResult<Object> removeSelectedUser(@RequestParam(value = "ids") List<Long> ids){
        boolean flag = userService.removeByIds(ids);
        if (flag){
            return CommonResult.success().setMessage(HttpMessageConst.REMOVE_SUCCESS_MESSAGE);
        }else {
            return CommonResult.failure();
        }
    }

    @ApiOperation(value = "导出用户列表(excel)")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<SysUser> list = userService.list();
        ExcelUtils.exportExcel(list, TITLE, SHEET_NAME,SysUser.class,
                TITLE+ System.currentTimeMillis()
                , response);
    }

    @ApiOperation(value = "导入用户列表(excel)")
    @PostMapping("/import")
    public CommonResult<Object> importUsers(MultipartFile file) throws Exception{
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        List<SysUser> list = ExcelImportUtil.importExcel(file.getInputStream(), SysUser.class, params);
        log.info("Start Import To DataBase");
        boolean flag = userService.saveBatch(list);
        if (flag){
            return CommonResult.success().setMessage(HttpMessageConst.IMPORT_SUCCESS_MESSAGE);
        }else {
            return CommonResult.failure();
        }
    }




}
