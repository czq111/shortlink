package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDto getUserByUsername(String username);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true or false
     */
    boolean hasUsername(String username);


    /**
     * 注册用户
     * @param requestParam
     */
    void register(UserRegisterReqDTO requestParam);

    /**
     * 修改用户
     * @param requestParam
     */
    void update(UserUpdateReqDTO requestParam);

    /**
     * 用户登录
     * @param requestParam 用户登录请求参数
     * @return 用户登录返回token
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 判断用户是否登录
     * @param username 用户名
     * @param token 请求参数
     * @return 返回是否登录
     */
    Boolean checkLogin(String username,String token);

    /**
     * 推出登录
     * @param username 用户名
     * @param token token
     */
    void logout(String username, String token);
}
