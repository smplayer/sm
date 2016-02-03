package com.sm.user.controller;

import com.sm.common.R;
import com.sm.common.controller.BasicController;
import com.sm.user.entity.User;
import com.sm.user.entity.UserInfo;
import com.sm.user.service.UserFacade;
import com.sm.user.service.UserInfoService;
import com.sm.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by Newbody on 10/8/2015.
 */
@Controller
//@RequestMapping("/user")
public class UserController extends BasicController {
    @Resource(name = "userFacade")
    private UserFacade userFacade;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @RequestMapping(value="mine", method = RequestMethod.GET)
    public ModelAndView mine(
            HttpServletRequest request,
            HttpSession session
    ) {
        UserInfo userInfo = userInfoService.get("userId", session.getAttribute("userId"));
        ModelAndView mav = new ModelAndView("jsp/mine");
        mav.addObject("userInfo", userInfo);
        return mav;
    }

    @RequestMapping(value="register", method = RequestMethod.GET)
    public ModelAndView registerForm(
            @RequestParam(required = false) String rid,
            @RequestParam(required = false) String redirectURL,
            HttpServletRequest request,
            HttpSession session
    ) {
        if (StringUtils.isBlank(rid)) {
            String referer = request.getHeader("referer");
            if (!StringUtils.isBlank(referer)) {
                if (!referer.contains("/login") && !referer.contains("/register")) {
                    rid = UUID.randomUUID().toString().replaceAll("-", "");
                    session.setAttribute(rid, referer);
                }
            }
        }

        ModelAndView mav = new ModelAndView("jsp/register");
        if (StringUtils.isNotBlank(rid)) {
            mav.addObject("rid", rid);
        }
        return mav;
    }

    @RequestMapping(value="register", method = RequestMethod.POST)
    public ModelAndView register(
            @RequestParam(required = false) String rid,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            HttpServletRequest request,
            HttpSession session
    ) {
        ModelAndView mav = new ModelAndView();

        R<Boolean> r = userFacade.register(username, password);
        if (r.getResult()) {
            mav.setViewName("redirect:/login");
        } else {
            mav.setViewName("redirect:/register");
            if (r.getData(UserFacade.MESSAGE).equals(UserFacade.USERNAME_DUPLICATE)) {
                mav.addObject("usernameError", "用户名已存在");
            } else if (r.getData(UserFacade.MESSAGE).equals(UserFacade.USERNAME_TOO_LONG)) {
                mav.addObject("usernameError", "用户名太长");
            } else if (r.getData(UserFacade.MESSAGE).equals(UserFacade.USERNAME_TOO_SHORT)) {
                mav.addObject("usernameError", "用户名太短");
            }
        }
        if (StringUtils.isNotBlank(rid)) {
            mav.addObject("rid", rid);
        }
        return mav;
    }

    @RequestMapping(value="login", method = RequestMethod.GET)
    public ModelAndView loginForm(
            @RequestParam(required = false) String rid,
            @RequestParam(required = false) String redirectURL,
            HttpServletRequest request,
            HttpSession session
    ) {
        if (StringUtils.isBlank(rid)) {
            String referer = request.getHeader("referer");
            if (!StringUtils.isBlank(referer)) {
                if (!referer.contains("/login") && !referer.contains("/register")) {
                    rid = UUID.randomUUID().toString().replaceAll("-", "");
                    session.setAttribute(rid, referer);
                }
            }
        }

        ModelAndView mav = new ModelAndView("jsp/login");
        if (StringUtils.isNotBlank(rid)) {
            mav.addObject("rid", rid);
        }
        return mav;
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    public ModelAndView login(
            @RequestParam(required = false) String rid,
            @RequestParam(required = false) Boolean remember,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        ModelAndView mav = new ModelAndView();
        R<Boolean> r = userFacade.login(username, password);

        if (r.getResult()) {
            User user = (User) r.getData("user");
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());

//            if (remember) {
//                Cookie usernameCookie = new Cookie("username", user.getUsername());
//                usernameCookie.setPath("/");
//                usernameCookie.setMaxAge(60 * 60 * 24 * 30);
//                response.addCookie(usernameCookie);
//                Cookie passwordCookie = new Cookie("password", user.getPassword());
//                passwordCookie.setMaxAge(60 * 60 * 24 * 30);
//                passwordCookie.setPath("/");
//                response.addCookie(passwordCookie);
//            }

            String redirectURL = (String) session.getAttribute(rid);
            session.removeAttribute(rid);
            if (StringUtils.isNotBlank(redirectURL)) {
                mav.setViewName("redirect:" + redirectURL);
                session.removeAttribute("redirectRUL");
            } else {
                mav.setViewName("redirect:/");
            }
        } else {
            String message = (String) r.getData(UserFacade.MESSAGE);
            if (UserFacade.NOT_MATCH.equals(message)) {
                mav.addObject("loginError", "用户名密码不匹配");
            } else if (UserFacade.USERNAME_NOT_EXIST.equals(message)) {
                mav.addObject("loginError", "用户名不存在");
            }
            if (StringUtils.isNotBlank(rid)) {
                mav.addObject("rid", rid);
            }
            mav.setViewName("redirect:/login");
        }
        return mav;
    }

    @RequestMapping(value="logout")
    public ModelAndView logout(HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView();
        session.invalidate();
        String referer = request.getHeader("referer");
        if (!StringUtils.isBlank(referer)) {
            mav.setViewName("redirect:" + referer);
        } else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    public static void main(String[] args) {
        try {
            String url = URLEncoder.encode("http://baidu.com?a=b&c=d&name=爱你一万年", "UTF-8");
            System.out.println(url);
            System.out.println(URLDecoder.decode(url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
