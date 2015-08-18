package com.ddup.sys.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 用户相关的操作
 * @author dznzyx
 * @date 2015年8月18日 下午8:10:58
 */
@RequestMapping(path = "/system/", method = { RequestMethod.GET, RequestMethod.POST })
@Controller("systemUser")
public class UserAction {

    /**
     * @Title: loginUI 
     * @Description: 
     * @return
     * @throws
     */
    @RequestMapping("login")
    public String loginUI() {
        return "/login.jsp";
    }
}
