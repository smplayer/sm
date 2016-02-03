package com.sm.user.controller;

import com.sm.user.entity.User;
import com.sm.user.service.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by Newbody on 10/17/2015.
 */
public class AdminAuthorityInterceptor extends HandlerInterceptorAdapter {
    @Resource(name = "userService")
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String contextPath = request.getContextPath();
//        System.out.println("contextPath:" + contextPath);
//        String servletPath = request.getServletPath().toString();
//        System.out.println("servletPath:" + servletPath);
//        String referer = request.getHeader("referer");
//        System.out.println("referer:" + referer);
//        if (referer != null) {
//            System.out.println("index of contextPath:" + referer.indexOf(contextPath));
//            String[] rfs = referer.split(contextPath);
//            for (String r : rfs) {
//                System.out.println("part of referer:" + r);
//            }
//            System.out.println("referer sub:" + referer.substring(referer.indexOf(contextPath) + contextPath.length()));
//        }
//        String curUrl = request.getServerName()
//                + request.getContextPath()
//                + request.getServletPath()
//                + "?"
//                + request.getQueryString();
//        System.out.println("curUrl:" + curUrl);
//        System.out.println("contains serverName:" + curUrl.contains(request.getServerName()));
//        System.out.println("request.getRequestURI:" + request.getRequestURI());
//        System.out.println("request.getRequestURL:" + request.getRequestURL());


        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            User user = userService.getById(userId);
            if(user.getUsername().equals("newbody")){
                return true;
            }
        } else {
            String redirectURL = null;
            if(request.getMethod().toUpperCase().equals("POST")){
                redirectURL = request.getHeader("referer");
            } else {
                StringBuffer requestURL = request.getRequestURL();
                String queryString = request.getQueryString();
                if(queryString != null){
                    requestURL.append("?").append(queryString);
                }
                redirectURL = requestURL.toString();
            }
            String rid = UUID.randomUUID().toString().replaceAll("-", "");
            request.getSession().setAttribute(rid, redirectURL);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login?rid=" + rid);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle ................");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("afterCompletion ................");
        super.afterCompletion(request, response, handler, ex);
    }
}
