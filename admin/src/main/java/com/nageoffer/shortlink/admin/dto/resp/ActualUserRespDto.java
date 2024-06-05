package com.nageoffer.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 用户返回参数响应
 */
@Data
public class ActualUserRespDto {

    private Long id;

    private String username;

    private String realName;

    private String phone;

    private String mail;
}
