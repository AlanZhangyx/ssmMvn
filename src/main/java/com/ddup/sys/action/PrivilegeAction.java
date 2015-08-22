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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddup.base.BaseAction;
import com.ddup.base.ToJSPException;
import com.ddup.sys.model.Privilege;
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
        return JSP_PREFIX+"/sys/privilegelist";
    }
    
    
    /**2
     * @Title: listJson
     * @Description: 返回用于CRUD的json数据
     * @return
     * @throws
     */
    @RequestMapping(value="/list/json")
    @ResponseBody
    public JSONObject listJson(String fuzzyWord,Integer page,Integer rows,
            Integer roldId//选择角色的权限
            ){
        resultJson=new JSONObject();
        try {
            //查询条件
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("fuzzyWord", fuzzyWord);
            List<Map<String,Object>> list=null;//结果list
            //查询数据库
            if(null!=roldId){//角色的权限
                list=privilegeService.listPrivilegesByRoldId(roldId);
            }else if (null==page||null==rows) {//全量查询
                list=privilegeService.listForCRUD(map);//分页list是Page<E>类型
            }else{//分页查询
                PageHelper.startPage(page, rows);
                list=privilegeService.listForCRUD(map);//分页list是Page<E>类型
                PageInfo<Map<String,Object>> p = new PageInfo<Map<String,Object>>(list);//取出分页统计信息statistic
                resultJson.put("total", p.getTotal());
            }
            //结果处理
            ProcessUtil.formatPage2ArrayList(list);//将list(Page<E>)转为他的父类ArrayList(不是Page也无所谓),并格式化时间
            resultJson.put("rows", list);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("查询权限列表");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**3
     * @Title: getJson
     * @Description: 返回用于CU的json数据
     * @return
     * @throws
     */
    @RequestMapping(value="/get/json")
    @ResponseBody
    public JSONObject getJson(Integer id,Boolean needPList
            ){
        resultJson=new JSONObject();
        try {
            Privilege record=privilegeService.selectByPrimaryKey(id);
            if(needPList){//如果需要父权限列表
                Integer pId=record.getParentId();
                List<Map<String, Object>> pList=privilegeService.listForCRUD(new HashMap<String,Object>());
                for (int i = 0; i < pList.size(); i++) {
                    Map<String,Object> item=pList.get(i);
                    if(pId==(Integer)item.get("id")){
                        item.put("checked", true);
                        pList.set(i, item);
                        break;
                    } 
                }
                //将pList放入放回结果
                resultJson.put("pList", pList);
            }
            resultJson.put("record", record);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("查询单个权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * @Title: addOne
     * @Description: 用于增加一条记录
     * @return
     * @throws
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public JSONObject addOne(Privilege record){
        resultJson=new JSONObject();
        try {
            privilegeService.insertSelective(record);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("增加一个权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * @Title: delete
     * @Description: 
     * @return
     * @throws
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
                privilegeService.deleteByPrimaryKeys(ids);
            }
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("删除一个权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }

    /**
     * @Title: update
     * @Description: 
     * @return
     * @throws
     */
    @RequestMapping(value="/update")
    @ResponseBody
    public JSONObject update(Privilege record){
        resultJson=new JSONObject();
        try {
            privilegeService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("修改一个权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
}