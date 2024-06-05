package com.nageoffer.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.ActualUserRespDto;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    //资源名称（用于接口限流）
    public static final String RESOURCE_NAME = "has-user";

    private final UserService userService;

    /**
     * 根据用户名查找用户
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username) {
        UserRespDto res = userService.getUserByUsername(username);
        return Results.success(res);
    }

    /**
     * 根据用户名查找不加密用户
     */
    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<ActualUserRespDto> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), ActualUserRespDto.class));
    }

    /**
     * 查询用户名是否存在  存在 ture 不存在 false
     */
    @GetMapping("/api/short-link/v1/user/has-user")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
//        boolean res = true; //默认存在该接口
//        Entry entry = null;
//        try {
//            entry = SphU.entry(RESOURCE_NAME);
//            res = userService.hasUsername(username);
//        } catch (BlockException e) {
//            System.out.print("限流");
//            return Results.success(true);  //限流 返回存在
//        } finally {
//            if (entry != null) {
//                entry.exit();
//            }
//        }
//        return Results.success(res);
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return Results.success();
    }


    /**
     * 修改用户
     */
    @PutMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserUpdateReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/short-link/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        UserLoginRespDTO res=userService.login(requestParam);
        return Results.success(res);
    }

    /**
     * 判断用户是否用户登录
     */
    @GetMapping("/api/short-link/v1/user/check-login")
    public Result<Boolean> login(@RequestParam("username") String username,@RequestParam("token") String token) {
        return Results.success(userService.checkLogin(username,token));
    }


}
