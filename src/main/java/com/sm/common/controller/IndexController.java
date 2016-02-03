package com.sm.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Newbody on 12/8/2015.
 */
@Controller
//无效
//@RequestMapping("/")
public class IndexController extends BasicController {

//    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("redirect:/product/all");
        return mav;
    }
}
