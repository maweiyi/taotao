package me.maweiyi.interceptor;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import me.maweiyi.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by maweiyi on 6/6/17.
 */
public class UserLoginHandlerInterceptor implements HandlerInterceptor {

    @Value("{SSO_TAOTAO_URL}")
    private String SSO_TAOTAO_URL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取ticket

        //判断ticket如果不为空，则表示用户登陆过

        //根据ticket查询用户信息

        //如果user不为空，则表示用户已经登陆过
        String ticket = CookieUtils.getCookieValue(request, "TT_TICKET");

        // 判断ticket如果为空，则表示用户未登录
        if (StringUtils.isBlank(ticket)) {
            // 转发到用户登录页面
            response.sendRedirect(this.userService.SSO_TAOTAO_URL + "page/login");
            return false;
        }

        // 根据ticket查询用户信息
        User user = this.userService.queryUserByTicket(ticket);
        // 判断user是否为空，如果为空，用户登录超时
        if (user == null) {
            // 转发到用户登录页面
            response.sendRedirect(this.userService.SSO_TAOTAO_URL + "page/login");
            return false;
        }

        // 如果user不为空，则表示用户已登录
        // 保存在ThreadLocal中
        UserThreadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
