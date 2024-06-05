package com.nageoffer.shortlink.admin.common.enums;

import com.nageoffer.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {


    USER_NULL("B000200","用户记录不存在"),
    USER_NAME_EXIT("B000201","用户名已存在"),
    USER_EXIT("B000202","用户记录已存在"),
    USER_SAVE_ERROR("B000203","用户记录新增失败");
  //  FIND_ERROR("B000204","访问频繁");
    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }



}
