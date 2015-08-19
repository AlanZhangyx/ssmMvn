package com.ddup.sys.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddup.base.BaseAction;
import com.ddup.sys.model.Privilege;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.utils.ProcessUtil;

/**
 * 权限相关操作
 * @author ZhangYaxu
 * @date 2015年8月18日
 */
@Controller
public class PrivilegeAction extends BaseAction {
    
    /**
     * 日志
     */
    private static final Logger LOGGER=Logger.getLogger(PrivilegeAction.class);
    
    @Resource
    private PrivilegeService privilegeService;
    
    /*    @ResponseBody
    public JSONObject listPrivilegesByRoldId(Integer roldId){
        resultJson=new JSONObject();
        try {
            
        } catch (Exception e) {
            String errorMsg="";
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }*/
    
    /**
     * 结果JSON，使用时先new
     */
    private JSONObject resultJson=null;
    
    
    @ResponseBody
    public JSONObject getMenuByUser(){
        resultJson=new JSONObject();
        try {
            
        } catch (Exception e) {
            String errorMsg="";
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 2
     * @Title: listPrivilegesByRoldId 
     * @Description: 获取所有权限，并为所有已经分配给roleId的权限加上checked:true
     * @param roldId
     * @return
     * @throws
     */
    @ResponseBody
    public JSONObject listPrivilegesByRoldId(@RequestParam("roldId") Integer roldId){
        resultJson=new JSONObject();
        try {
            List<Map<String,Object>> list=privilegeService.listPrivilegesByRoldId(roldId);
            resultJson.put("list", list);
        } catch (Exception e) {
            String errorMsg="";
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
}
