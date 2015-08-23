package com.ddup.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ddup.sys.model.User;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.utils.ProcessUtil;

/**
 * @Description: 框架中心控制拦截器
 * (前提是：只是为了对付某些知道URL的人在地址栏输入的恶意访问，如果正常操作，没有权限我不会给你点击机会)             
 * 逻辑：
 * 1 这里只拦截配置过的需要拦截的地址
 * 2 判断请求是否是AJAX类型
 *      1 是：
 *          1 在未登录前，返回{state:403,errorMsg:访问被拒绝，请先登录}
 *          2 登陆后，检查用户是否有访问的url权限
 *              1 有：放行
 *              2 没有：返回{state:403,errorMsg:访问被拒绝，您没有权限}
 *      2 否(普通请求)：
 *          1 在未登录前，任何访问url都跳转到havenotlogin页面,再尤其跳转到login页面(解决iframe跳转问题的方式)
 *          2 登录成功后，检查用户是否有访问的url权限
 *              1 有：放行
 *              2 没有：跳转到非法请求页面
 *              
 * 例外注意：
 * 1 路径中有"/get/",只会截取包括"get"的之前的路径去校验，如privilege/get/aaaa/bbb 校验:privilege/get
 * 2 专有路径之check:路径中有"/check/"直接放行，这是为了校验的请求
 *          
 * @author dznzyx
 * @date 2015年8月21日 下午12:08:03
 */
public class CenterInterceptor extends HandlerInterceptorAdapter {
    
    /**
     * Spring MVC粗略理解
     * 1 DispatcherServlet将解析请求URL，调用HandlerMapping,来根据请求为我们寻找Handler(有多个HandlerMapping实现)
     *   如果找不到Handler就用defaultHandler直接回到DispatcherServlet，然后响应response客户端
     * 2 HandlerMapping发现的确有请求的Handler，它为我们指定一个请求链HandlerExecutionChain
     *   其中包含多个HandlerInterceptor和一个handler
     * 3 DispatcherServlet根据handler选择一个合适的handlerAdapter(成功获取handlerAdapter，就会执行拦截器的preHandle)，
     *   然后将request中的数据提取并做一些自定义的验证操作等，然后把数据放入具体的我们自己写的Handler(Controller)，执行相应方法
     * 4 根据我们方法结束后的返回ModelAndView，为我们在Spring容器中从ViewResolver链中找到一个具体的ViewResolver，然后将
     *   我们的Model绑定到View上。回到DispatcherServlet，响应response请求
     */
    
    @Resource
    private PrivilegeService privilegeService;
    
    /**
     * Controller之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String XMLHttpRequest=request.getHeader("X-Requested-With");//判断请求是否是AJAX类型
        String requestURI=request.getRequestURI();//标识符从contextPath到?之前
        HttpSession session=request.getSession();//HttpSession
        User user=(User)session.getAttribute("user");//登陆的用户
        //1 AJAX
        if (null!=XMLHttpRequest && "XMLHttpRequest".equals(XMLHttpRequest)) {
            //没有登陆
            if(null==user){
                ProcessUtil.responseJson(response,"{'state':403,'errorMsg':'访问被拒绝，请先登录'}");
                return false;
            }
            //登陆了但没权限
            if(!hasPrivilege(user.getId(),requestURI)){
                ProcessUtil.responseJson(response,"{'state':403,'errorMsg':'访问被拒绝，您没有权限'}");
                return false;
            }
        }else{//2 普通请求
            //没有登陆
            if(null==user){
                request.getRequestDispatcher("/havenotlogin.jsp").forward(request, response);
                return false;
            }
            //登陆了但没权限
            if(!hasPrivilege(user.getId(),requestURI)){
                request.getRequestDispatcher("/WEB-INF/jsp/error/403.jsp").forward(request, response);
                return false;
            }
        }
        return true;//重重考验没有被false，那么就true
    }

    /**
     * @Title: hasPrivilege 
     * @Description: 检查用户是否有requestURI的权限
     * @param id
     * @param requestURI
     * @return 有返回true，否则false
     * @throws
     */
    private boolean hasPrivilege(Integer id, String requestURI) {
        //专有路径无需校验:check
        if(requestURI.indexOf("/check")>-1){
           return true; 
        }
        
        //查询的权限以get开头，只在数据库中分配一次获取(get)权限
        int getIndex=requestURI.indexOf("/get/");
        if (getIndex>-1) {
            requestURI=requestURI.substring(0, getIndex+4);
        }
        
        //正常主体检查
        List<Map<String, Object>> pList=privilegeService.listPrivilegesByUserId(id);
        for (int i = 0; i < pList.size(); i++) {
            Map<String, Object> pMap=pList.get(i);
            if(pMap.get("actionUrl").equals(requestURI)){
                return true;
            }
        }
        return false;
    }

}