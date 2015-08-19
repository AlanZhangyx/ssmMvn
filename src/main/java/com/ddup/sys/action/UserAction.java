package com.ddup.sys.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ddup.base.BaseAction;
import com.ddup.sys.model.User;
import com.ddup.sys.service.UserService;

/**
 * @Description: 用户相关的操作
 * @author dznzyx
 * @date 2015年8月18日 下午8:10:58
 */
@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
@Controller("systemUser")
public class UserAction extends BaseAction{
    
    @Resource
    private UserService userService;
    
    /**1
     * @Title: loginUI 
     * @Description: 到登陆页
     * @return
     * @throws
     */
    @RequestMapping("/login")
    public String loginUI() {
        return "/login";
    }
    
    /**2
     * @Title: login 
     * @Description: 登陆
     * @param request
     * @param record
     * @return
     * @throws
     */
    @RequestMapping("/user/login")
    public ModelAndView login(HttpServletRequest request,User record) {
        ModelAndView mav=new ModelAndView("/login");
        User user=userService.getByUserNamePassword(record);
        if(null==user){//没有就返回
            mav.addObject("errorMsg", "用户名或密码错误");
            return mav;
        }
        //成功登陆
        HttpSession session=request.getSession();
        session.setAttribute("user", user);
        mav.setViewName(JSP_PREFIX+"framework/main");
        return mav;
    }
    
    /**3
     * @Title: index 
     * @Description: 到index页面
     * @return
     * @throws
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(null==user){//没有就返回
            return "/login";
        }
        return JSP_PREFIX+"framework/main";
    }
    
    
}
