package com.ddup.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddup.base.BaseAction;
import com.ddup.base.ToJSPException;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.utils.ProcessUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 权限相关操作
 * @author ZhangYaxu
 * @date 2015年8月18日
 */
@RequestMapping(value="/privilege",method = { RequestMethod.GET, RequestMethod.POST })
@Controller
public class PrivilegeAction extends BaseAction {
    
    /**
     * 日志
     */
    private static final Logger LOGGER=Logger.getLogger(PrivilegeAction.class);
    
    @Resource
    private PrivilegeService privilegeService;
    
    /**
     * 结果JSON，使用时先new
     */
    private JSONObject resultJson=null;
  
    
    /*********************Business Process***************************/
    /**1
     * @throws ToJSPException
     * @Title: list 
     * @Description: 跳转到list页面
     * @return
     * @throws
     */
    @RequestMapping("/list")
    public String list() throws ToJSPException{
        try {
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到权限页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return JSP_PREFIX+"/sys/userlist";
    }
    
    
    /**2
     * @Title: listJson
     * @Description: 返回用于CRUD的json数据
     * @return
     * @throws
     */
    @RequestMapping(value="/list/json")
    @ResponseBody
    public JSONObject listJson(String fuzzyWord,int page,int rows){
        resultJson=new JSONObject();
        try {
            //查询条件
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("fuzzyWord", fuzzyWord);
            //分页查询
            PageHelper.startPage(page, rows);
            List<Map<String,Object>> list=privilegeService.listForCRUD(map);//分页list是Page<E>类型
            PageInfo<Map<String,Object>> p = new PageInfo<Map<String,Object>>(list);//取出分页统计信息statistic
            
            //将list(Page<E>)转为他的父类ArrayList,
            ProcessUtil.formatPage2ArrayList(list);
            
            //结果返回
            resultJson.put("rows", list);
            resultJson.put("total", p.getTotal());
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("查询权限列表");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**3
     * @Title: listPrivilegesByRoldId 
     * @Description: 获取所有权限，并为所有已经分配给roleId的权限加上checked:true
     * @param roldId
     * @return
     * @throws
     */
    @RequestMapping(value="/listPrivilegesByRoldId",produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject listPrivilegesByRoldId(@RequestParam("roldId") Integer roldId){
        resultJson=new JSONObject();
        try {
            //查询条件
            List<Map<String,Object>> list=privilegeService.listPrivilegesByRoldId(roldId);
            resultJson.put("list", list);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("获取角色的权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
}
