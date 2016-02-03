package com.sm.user.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Newbody on 11/22/2015.
 */
public class AuthorityInterceptorForAjax extends HandlerInterceptorAdapter {
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
//                + request.getServerPort()
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
            return true;
        } else {
//            StringBuffer requestURL = request.getRequestURL();
//            String queryString = request.getQueryString();
//            if (queryString != null) {
//                requestURL.append("?").append(queryString);
//            }
//            String redirectURL = URLEncoder.encode(requestURL.toString(), "UTF-8");
//            String contextPath = request.getContextPath();
//            System.out.println(contextPath + "/login?redirectURL=" + redirectURL);
//            String loggingUrl = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login";
//            String referer = URLEncoder.encode(request.getHeader("referer"), "UTF-8");
            String referer = request.getHeader("referer");
            String rid = UUID.randomUUID().toString().replaceAll("-", "");
            request.getSession().setAttribute(rid, referer);

            String loggingUrl = request.getContextPath() + "/login?rid=" + rid;

            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            Map<String, Object> data = new HashMap<>();
            data.put("redirect", true);
//            data.put("loggingUrl", loggingUrl);
            data.put("redirectURL", loggingUrl);
            writer.append(JSON.toJSONString(data));
            writer.flush();
            writer.close();
            return false;
        }
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