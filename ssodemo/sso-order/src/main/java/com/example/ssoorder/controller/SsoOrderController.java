package com.example.ssoorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SsoOrderController {

    @RequestMapping("/queryInfo")
    public String queryOrderInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HttpSession session = httpServletRequest.getSession();
        httpServletRequest.setAttribute("userName",session.getAttribute("userName"));
        return "orderInfo";
    }
    @RequestMapping("/createSession")
    @ResponseBody
    public void createSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HttpSession session = httpServletRequest.getSession();
        String url=httpServletRequest.getParameter("url");
        System.out.println("createSession() sessionId="+session.getId()+",url="+url);
        session.setAttribute("userName",httpServletRequest.getAttribute("userName"));
        try {
            httpServletResponse.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
