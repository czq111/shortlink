package com.nageoffer.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户登录返回参数
 */
@Data
@AllArgsConstructor
public class UserLoginRespDTO {
    private String token;
}
