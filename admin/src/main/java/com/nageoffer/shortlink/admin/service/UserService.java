package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
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
}
