package com.ddup.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ddup.base.BaseAction;
import com.ddup.base.ToJSPException;
import com.ddup.sys.model.Role;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.sys.service.RoleService;
import com.ddup.utils.ProcessUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 角色相关操作
 * @author zyx
 * @date 2015年8月20日 下午2:53:42
 */
@RequestMapping(value="/role",method = { RequestMethod.GET, RequestMethod.POST })
@Controller
public class RoleAction extends BaseAction {
    
    private static final String JSP_PREFIX="/WEB-INF/jsp/sys/role";//Role前缀
    private static final Logger LOGGER=Logger.getLogger(RoleAction.class);//日志
    
    @Resource
    private RoleService roleService;
    @Resource
    private PrivilegeService privilegeService;
    
    /**
     * 结果JSON，使用时先new
     */
    private JSONObject resultJson=null;
  
    
    /*********************Business Process***************************/
    /**
     * 1 代表此角色
     * 跳转到list页面
     */
    @RequestMapping("/query")
    public String list() throws ToJSPException{
        try {
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到角色页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return JSP_PREFIX+"/list";
    }
    
    
    /**
     * 2.1 列表页面数据填充
     * 返回用于CRUD的json数据
     */
    @RequestMapping(value="/query/jsonlist")
    @ResponseBody
    public JSONObject jsonList(String fuzzyWord,Integer page,Integer rows
            ){
        resultJson=new JSONObject();
        try {
            //查询条件
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("fuzzyWord", fuzzyWord);
            List<Map<String,Object>> list=null;//结果list
            //查询数据库
            if (null==page||null==rows) {//全量查询
                list=roleService.listForCRUD(map);//分页list是Page<E>类型
            }else{//分页查询
                PageHelper.startPage(page, rows);
                list=roleService.listForCRUD(map);//分页list是Page<E>类型
                PageInfo<Map<String,Object>> p = new PageInfo<Map<String,Object>>(list);//取出分页统计信息statistic
                resultJson.put("total", p.getTotal());
            }
            //结果处理
            ProcessUtil.formatPage2ArrayList(list);//将list(Page<E>)转为他的父类ArrayList(不是Page也无所谓),并格式化时间
            resultJson.put("rows", list);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("查询角色列表");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }

    
    /**
     * 3.1 跳转到add页面
     */
    @RequestMapping("/query/addUI")
    public ModelAndView addUI(HttpServletRequest request) throws ToJSPException{
        ModelAndView mav=new ModelAndView(JSP_PREFIX+"/add");
        try {
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(privilegeService.listForZtree());
            mav.addObject("list",jsonArray);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到角色增加页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return mav;
    } 
    
    
    /**
     * 3.2 用于增加一条记录
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public JSONObject add(Role record,Integer... pIds){
        resultJson=new JSONObject();
        try {
            roleService.insert(record,pIds);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("增加一个角色");
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
    @RequestMapping(value="/delete")
    @ResponseBody
    public JSONObject delete(Integer... ids){
        resultJson=new JSONObject();
        try {
            if (ids.length>0) {
                roleService.deleteByPrimaryKeys(ids);
            }
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("删除角色");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 5.1 跳转到update页面
     */
    @RequestMapping("/query/updateUI")
    public ModelAndView updateUI(Integer id) throws ToJSPException{
        ModelAndView mav=new ModelAndView(JSP_PREFIX+"/update");
        try {
            //角色
            Role record=roleService.selectByPrimaryKey(id);
            Map<String,Object> resultMap=privilegeService.listPrivilegesByRoleId(id);
            //查询出角色的权限列表
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> pList=(List<Map<String, Object>>)resultMap.get("listAllWithChkSign");
            
            
            //将pList放入放回结果
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(pList);
            
            mav.addObject("pIds", resultMap.get("pIds"));
            mav.addObject("pNames", resultMap.get("pNames"));
            mav.addObject("list", jsonArray);
            mav.addObject("role", record);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到角色修改页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return mav;
    } 

    /**
     * 5.2 update
     */
    @RequestMapping(value="/update")
    @ResponseBody
    public JSONObject update(Role record,Integer... pIds){
        resultJson=new JSONObject();
        try {
            roleService.updateByPrimaryKeySelective(record,pIds);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("修改一个角色");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 6 唯一性检查
     */
    @RequestMapping(value="/query/check")
    @ResponseBody
    public boolean check(@RequestParam String name,Integer id){
        if (null!=id && 0!=id) {//修改的情况
            String dbName=roleService.selectByPrimaryKey(id).getName();
            if(name.equals(dbName)){
                return true;
            }
        }
        Role record=new Role();
        record.setName(name);
        return roleService.checkUnique(record);
    }
}
