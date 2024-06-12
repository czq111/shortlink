package com.nageoffer.shortlink.project.interceptor;

import com.alibaba.fastjson2.JSON;
import com.nageoffer.shortlink.project.common.biz.user.UserContext;
import com.nageoffer.shortlink.project.common.biz.user.UserInfoDTO;
import com.nageoffer.shortlink.project.common.convention.errorcode.BaseErrorCode;
import com.nageoffer.shortlink.project.common.convention.result.Results;
import com.nageoffer.shortlink.project.dao.entity.UserDO;
import com.nageoffer.shortlink.project.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //静态方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            log.info("静态方法直接放行");
            return true;
        }

        String token = request.getHeader("Token");
        if (token == null) {
            //  Result res = Result.fail(-999, "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Results.failure(BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR.code(), BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR.message())));
            log.info("未登录");
            return false;
        }
        if(JWTUtils.checkToken(token)==null){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Results.failure(BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR.code(), BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR.message())));
            log.info("未登录");
            return false;
        }
        String username = (String) JWTUtils.checkToken(token).get("userName");
        //  String o = (String)redisTemplate.opsForValue().get("TOKEN_" + token);

        String o = (String) redisTemplate.opsForHash().get("login:" + username, token);
        UserDO sysUser = JSON.parseObject(o, UserDO.class);
        if (sysUser == null) {
            // Result res = Result.fail(-999, "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Results.failure(BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR.code(), BaseErrorCode.IDEMPOTENT_TOKEN_NULL_ERROR.message())));
            log.info("未登录");
            return false;
        }
        log.info("已经登录，放行");
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userId(sysUser.getId()+"")
                .username(sysUser.getUsername())
                .realName(sysUser.getRealName())
                .token(token).build();
        UserContext.setUser(userInfoDTO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
