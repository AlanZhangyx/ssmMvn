package com.ddup.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ddup.base.ToJSPException;
import com.ddup.sys.model.User;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.sys.service.RoleService;
import com.ddup.sys.service.UserService;
import com.ddup.sys.service.impl.UserServiceImpl;
import com.ddup.utils.ProcessUtil;

/**
 * @Description: 用户相关的操作
 * @author zyx
 * @date 2015年8月18日 下午8:10:58
 */
@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
@Controller("systemUser")
public class UserAction{
    
    protected static final String JSP_PREFIX="/WEB-INF/jsp";//通用的jsp前缀
    protected static final String JSP_PREFIX_USER="/WEB-INF/jsp/sys/user";//USER前缀
    
    private static final Logger LOGGER=Logger.getLogger(UserAction.class);//日志
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PrivilegeService privilegeService;
    
    /**
     * 结果JSON，使用时先new
     */
    private JSONObject resultJson=null;
    
    
    /*********************System Module Process***************************/
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
    @RequestMapping("/sys/login")
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
        mav.setViewName("redirect:/index");
        return mav;
    }
    
    /**3
     * @Title: index 
     * @Description: 到index页面
     * @return
     * @throws
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mav=new ModelAndView("/login");
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        if(null==user){//没有就返回
            return mav;
        }
        //查询用户菜单
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(privilegeService.listMenusByUserId(user.getId()));
        mav.addObject("menu",jsonArray);
        mav.setViewName(JSP_PREFIX+"/framework/main");
        return mav;
    }

    /**4
     * @Title: signout 
     * @Description: 注销
     * @param request
     * @return
     * @throws
     */
    @RequestMapping("/sys/signout")
    public ModelAndView signout(HttpServletRequest request) {
        ModelAndView mav=new ModelAndView("/login");
        HttpSession session=request.getSession();
        try {
            session.invalidate();
        } catch (IllegalStateException e) {
            return mav;
        }
        return mav;
    }
    
    /*********************Business Process***************************/
    /**
     * 1 代表此用户
     * 跳转到list页面
     */
    @RequestMapping("/user/query")
    public String list() throws ToJSPException{
        try {
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到用户页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return JSP_PREFIX_USER+"/list";
    }
    
    
    /**
     * 2.1 列表页面数据填充
     * 返回用于CRUD的json数据
     */
    @RequestMapping(value="/user/query/jsonlist")
    @ResponseBody
    public JSONObject jsonList(String fuzzyWord,Integer page,Integer rows
            ){
        resultJson=new JSONObject();
        try {
            //查询条件
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("fuzzyWord", fuzzyWord);
            List<User> list=null;//结果list
            //查询数据库
            if (null==page||null==rows) {//全量查询
                list=userService.listForCRUD(map);
            }else{//手写分页查询
                map.put("offset", (page-1)*rows);
                map.put("limit", rows);
                list=userService.listForCRUD(map);
                resultJson.put("total", userService.listForCRUDCount(map));
            }
            //结果处理
            List<Map<String, Object>> resultList=UserServiceImpl.formatUserList2ArrayList(list);
            
            resultJson.put("rows", resultList);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("查询用户列表");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }

    
    /**
     * 3.1 跳转到add页面
     */
    @RequestMapping("/user/add/addUI")
    public ModelAndView addUI(HttpServletRequest request) throws ToJSPException{
        ModelAndView mav=new ModelAndView(JSP_PREFIX_USER+"/add");
        try {
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(roleService.listForZtree());
            mav.addObject("list",jsonArray);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到用户增加页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return mav;
    } 
    
    
    /**
     * 3.2 用于增加一条记录
     */
    @RequestMapping(value="/user/add")
    @ResponseBody
    public JSONObject add(User record,Integer... rIds){
        resultJson=new JSONObject();
        try {
            userService.insert(record,rIds);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("增加一个用户");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 4 delete
     * 测试：service方法加了事务，那么它调用mapper应该会exception，那么就会Rollback
     * 先在界面选择3条数据，然后手动从数据库删除其中1条，
     * 然后界面发送删除请求，看看是否回滚
     */
    @RequestMapping(value="/user/delete")
    @ResponseBody
    public JSONObject delete(Integer... ids){
        resultJson=new JSONObject();
        try {
            userService.deleteByPrimaryKeys(ids);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("删除用户");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 5.1 跳转到update页面
     */
    @RequestMapping("/user/update/updateUI")
    public ModelAndView updateUI(Integer id) throws ToJSPException{
        ModelAndView mav=new ModelAndView(JSP_PREFIX_USER+"/update");
        try {
            //用户
            User record=userService.selectByPrimaryKey(id);
            Map<String,Object> resultMap=roleService.listByUserId(id);
            //查询出角色的权限列表
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> pList=(List<Map<String, Object>>)resultMap.get("listAllWithChkSign");
            
            
            //将pList放入放回结果
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(pList);
            
            mav.addObject("rIds", resultMap.get("rIds"));
            mav.addObject("rNames", resultMap.get("rNames"));
            mav.addObject("list", jsonArray);
            mav.addObject("user", record);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到用户修改页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return mav;
    } 

    /**
     * 5.2 update
     */
    @RequestMapping(value="/user/update")
    @ResponseBody
    public JSONObject update(User record,Integer... rIds){
        resultJson=new JSONObject();
        try {
            userService.updateByPrimaryKeySelective(record,rIds);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("修改一个用户");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 6 唯一性检查
     */
    @RequestMapping(value="/user/query/check")
    @ResponseBody
    public boolean check(@RequestParam String userName,Integer id){
        if (null!=id && 0!=id) {//修改的情况
            String dbName=userService.selectByPrimaryKey(id).getUserName();
            if(userName.equals(dbName)){
                return true;
            }
        }
        User record=new User();
        record.setUserName(userName);
        return userService.checkUnique(record);
    }
    
    
}
