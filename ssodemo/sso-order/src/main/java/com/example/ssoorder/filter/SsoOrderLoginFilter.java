package com.example.ssoorder.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
@ServletComponentScan
public class SsoOrderLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter......");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession httpSession = httpServletRequest.getSession(false);
        String token = httpServletRequest.getParameter("token");
        String userName = httpServletRequest.getParameter("userName");
        if (httpSession == null && StringUtils.isBlank(token)) {
            System.out.println("未登录,即将跳转到登录页面");
            httpServletResponse.sendRedirect("http://localhost:8081/provider/index?url=http://localhost:8082/order/queryInfo");
            return;
        }
        if (httpSession != null || userName !=null) {
            servletRequest.setAttribute("userName",userName);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        System.out.println(httpServletRequest.getRequestURI());
        if (StringUtils.isNotBlank(token)) {
            httpServletResponse.sendRedirect("http://localhost:8081/provider/checkToken?url=http://localhost:8082/order/queryInfo&token="+token);
            return;
          }

    }
}
